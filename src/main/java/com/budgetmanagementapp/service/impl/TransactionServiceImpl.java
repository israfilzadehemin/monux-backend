package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.ACCOUNT_FROM;
import static com.budgetmanagementapp.utility.Constant.ACCOUNT_TO;
import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.INSUFFICIENT_BALANCE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_CATEGORY_ID_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_ACCOUNT_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.InOutTransaction;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.TransferTransaction;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.AccountNotFoundException;
import com.budgetmanagementapp.exception.CategoryNotFoundException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.model.TransferRequestModel;
import com.budgetmanagementapp.model.TransferResponseModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.repository.CustomCategoryRepository;
import com.budgetmanagementapp.repository.CustomTagRepository;
import com.budgetmanagementapp.repository.InOutTransactionRepository;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.TransactionType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final CategoryRepository categoryRepo;
    private final CustomCategoryRepository customCategoryRepo;
    private final TagRepository tagRepository;
    private final CustomTagRepository customTagRepo;
    private final InOutTransactionRepository inOutRepo;

    @Override
    @Transactional
    public InOutResponseModel createInOutTransaction(InOutRequestModel requestBody,
                                                     TransactionType transactionType,
                                                     String username) {
        CustomValidator.validateIncomeModel(requestBody);

        User user = findUserByUsername(username);

        Account account = accountRepo.byIdAndUser(requestBody.getAccountId(), user)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                format(UNAUTHORIZED_ACCOUNT_MSG, username, requestBody.getAccountId())));

        if (transactionType.equals(TransactionType.OUTCOME)
                && !account.isAllowNegative()
                && account.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }

        Optional<Category> category = categoryRepo
                .byIdAndType(requestBody.getCategoryId(), CategoryType.valueOf(transactionType.name()).name());

        Optional<CustomCategory> customCategory = customCategoryRepo
                .byIdAndUserAndType(
                        requestBody.getCategoryId(),
                        user,
                        CategoryType.valueOf(transactionType.name()).name());

        if (category.isEmpty() && customCategory.isEmpty()) {
            throw new CategoryNotFoundException(format(INVALID_CATEGORY_ID_MSG, requestBody.getCategoryId()));
        }

        List<Tag> tags = requestBody.getTagIds()
                .stream()
                .filter(id -> tagRepository.byId(id).isPresent())
                .map(id -> tagRepository.byId(id).get())
                .collect(Collectors.toList());

        List<CustomTag> customTags = requestBody.getTagIds()
                .stream()
                .filter(id -> customTagRepo.byIdAndUser(id, user).isPresent())
                .map(id -> customTagRepo.byIdAndUser(id, user).get())
                .collect(Collectors.toList());

        InOutTransaction transaction =
                buildInOutTransaction(
                        requestBody, user, account, category, customCategory, tags, customTags, transactionType);

        Map<String, Account> accounts = new HashMap<>();
        if (transactionType == TransactionType.INCOME) {
            accounts.put(ACCOUNT_TO, account);
        } else {
            accounts.put(ACCOUNT_FROM, account);
        }

        updateBalance(transactionType, requestBody.getAmount(), accounts);

        log.info(format(IN_OUT_TRANSACTION_CREATED_MSG, user.getUsername(), buildIncomeResponseModel(transaction)));
        return buildIncomeResponseModel(transaction);
    }

    @Override
    @Transactional
    public TransferResponseModel createTransferTransaction(TransferRequestModel requestBody,
                                                           TransactionType transactionType, String username) {
        CustomValidator.validateTransferModel(requestBody);
        User user = findUserByUsername(username);

        Account accountFrom = accountRepo.byIdAndUser(requestBody.getAccountFromId(), user)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                format(UNAUTHORIZED_ACCOUNT_MSG, username, requestBody.getAccountFromId())));

        Account accountTo = accountRepo.byIdAndUser(requestBody.getAccountToId(), user)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                format(UNAUTHORIZED_ACCOUNT_MSG, username, requestBody.getAccountToId())));

        if (!accountFrom.isAllowNegative() && accountFrom.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, accountFrom.getName()));
        }

        Map<String, Account> accounts = new HashMap<>() {{
            put(ACCOUNT_FROM, accountFrom);
            put(ACCOUNT_TO, accountTo);
        }};

        TransferTransaction transaction = buildTransferTransaction(requestBody, user, accountFrom, accountTo);
        updateBalance(TransactionType.TRANSFER, requestBody.getAmount(), accounts);
        log.info(format(TRANSFER_TRANSACTION_CREATED_MSG, user.getUsername(), buildTransferResponseModel(transaction)));

        return buildTransferResponseModel(transaction);
    }

    private TransferResponseModel buildTransferResponseModel(TransferTransaction transaction) {
        return TransferResponseModel.builder()
                .transferTransactionId(transaction.getTransferTransactionId())
                .creationDateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .accountFrom(transaction.getAccountFrom().getAccountId())
                .accountTo(transaction.getAccountTo().getAccountId())
                .build();
    }

    private TransferTransaction buildTransferTransaction(TransferRequestModel requestBody, User user,
                                                         Account accountFrom,
                                                         Account accountTo) {
        return TransferTransaction.builder()
                .transferTransactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.TRANSFER.name())
                .creationDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getCreationDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .user(user)
                .build();
    }

    private void updateBalance(TransactionType transactionType, BigDecimal amount, Map<String, Account> accounts) {
        switch (transactionType) {
            case INCOME:
                accounts.get(ACCOUNT_TO).setBalance(accounts.get(ACCOUNT_TO).getBalance().add(amount));
                break;
            case OUTCOME:
                accounts.get(ACCOUNT_FROM).setBalance(accounts.get(ACCOUNT_FROM).getBalance().subtract(amount));
                break;
            case TRANSFER:
                accounts.get(ACCOUNT_FROM).setBalance(accounts.get(ACCOUNT_FROM).getBalance().subtract(amount));
                accounts.get(ACCOUNT_TO).setBalance(accounts.get(ACCOUNT_TO).getBalance().add(amount));
                break;
        }
        accountRepo.save(accounts.get(ACCOUNT_FROM));
        accountRepo.save(accounts.get(ACCOUNT_TO));
    }

    private InOutTransaction buildInOutTransaction(InOutRequestModel requestBody, User user,
                                                   Account account, Optional<Category> category,
                                                   Optional<CustomCategory> customCategory, List<Tag> tags,
                                                   List<CustomTag> customTags, TransactionType type) {
        InOutTransaction transaction = InOutTransaction.builder()
                .inOutTransactionId(UUID.randomUUID().toString())
                .creationDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getCreationDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .transactionType(type.name())
                .account(account)
                .tags(tags)
                .customTags(customTags)
                .user(user)
                .build();

        category.ifPresent(transaction::setCategory);
        customCategory.ifPresent(transaction::setCustomCategory);

        inOutRepo.save(transaction);
        return transaction;
    }

    private InOutResponseModel buildIncomeResponseModel(InOutTransaction transaction) {
        InOutResponseModel responseModel = InOutResponseModel.builder()
                .transactionId(transaction.getInOutTransactionId())
                .creationDateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .accountId(transaction.getAccount().getAccountId())
                .tagIds(transaction.getTags()
                        .stream()
                        .map(Tag::getTagId)
                        .collect(Collectors.toList()))
                .customTagIds(transaction.getCustomTags()
                        .stream()
                        .map(CustomTag::getCustomTagId)
                        .collect(Collectors.toList()))
                .build();

        if (!Objects.isNull(transaction.getCategory())) {
            responseModel.setCategoryId(transaction.getCategory().getCategoryId());
        }

        if (!Objects.isNull(transaction.getCustomCategory())) {
            responseModel.setCategoryId(transaction.getCustomCategory().getCustomCategoryId());
        }

        return responseModel;
    }

    private User findUserByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, username)));
    }

}
