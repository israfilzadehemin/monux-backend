package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.INSUFFICIENT_BALANCE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_CATEGORY_ID;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_ACCOUNT_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.InOutTransaction;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.AccountNotFoundException;
import com.budgetmanagementapp.exception.CategoryNotFoundException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.repository.CustomCategoryRepository;
import com.budgetmanagementapp.repository.CustomTagRepository;
import com.budgetmanagementapp.repository.InOutTransactionRepository;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.InOutTransactionService;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.TransactionType;
import java.math.BigDecimal;
import java.util.List;
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
public class InOutTransactionServiceImpl implements InOutTransactionService {
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
            throw new CategoryNotFoundException(format(INVALID_CATEGORY_ID, requestBody.getCategoryId()));
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

        updateBalance(transactionType, requestBody.getAmount(), account);

        log.info(format(IN_OUT_TRANSACTION_CREATED_MSG, user.getUsername(), buildIncomeResponseModel(transaction)));
        return buildIncomeResponseModel(transaction);
    }

    private void updateBalance(TransactionType transactionType, BigDecimal amount, Account account) {
        if (transactionType.equals(TransactionType.INCOME)) {
            account.setBalance(account.getBalance().add(amount));
        } else if (transactionType.equals(TransactionType.OUTCOME)) {
            account.setBalance(account.getBalance().subtract(amount));
        }
        accountRepo.save(account);
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
