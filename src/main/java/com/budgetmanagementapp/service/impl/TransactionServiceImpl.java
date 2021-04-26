package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.ACCOUNT_FROM;
import static com.budgetmanagementapp.utility.Constant.ACCOUNT_TO;
import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_TRANSACTIONS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INSUFFICIENT_BALANCE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_CATEGORY_ID_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.NO_EXISTING_TRANSACTION_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_ACCOUNT_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_TRANSACTION_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.DebtTransaction;
import com.budgetmanagementapp.entity.InOutTransaction;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.TransferTransaction;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.AccountNotFoundException;
import com.budgetmanagementapp.exception.CategoryNotFoundException;
import com.budgetmanagementapp.exception.NoExistingTransactionException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.TransactionNotFoundException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.DebtRequestModel;
import com.budgetmanagementapp.model.DebtResponseModel;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.model.TransactionResponseModel;
import com.budgetmanagementapp.model.TransferRequestModel;
import com.budgetmanagementapp.model.TransferResponseModel;
import com.budgetmanagementapp.model.UpdateDebtRequestModel;
import com.budgetmanagementapp.model.UpdateInOutRequestModel;
import com.budgetmanagementapp.model.UpdateTransferRequestModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.repository.CustomCategoryRepository;
import com.budgetmanagementapp.repository.CustomTagRepository;
import com.budgetmanagementapp.repository.DebtTransactionRepository;
import com.budgetmanagementapp.repository.InOutTransactionRepository;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.TransferTransactionRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.TransactionType;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private final TransferTransactionRepository transferRepo;
    private final DebtTransactionRepository debtRepo;

    @Override
    @Transactional
    public InOutResponseModel createTransaction(InOutRequestModel requestBody,
                                                TransactionType type,
                                                String username) {
        CustomValidator.validateIncomeModel(requestBody);

        User user = userByUsername(username);
        Account account = accountById(requestBody.getAccountId(), user);

        if (type.equals(TransactionType.OUTCOME) && !account.isAllowNegative()
                && account.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }

        Optional<Category> category = categoryByIdAndType(requestBody, type);
        Optional<CustomCategory> customCategory = customCategoryByIdAndType(requestBody, type, user);

        checkCategory(requestBody, category, customCategory);

        List<Tag> tags = tagsByIds(requestBody);
        List<CustomTag> customTags = customTagsByIds(requestBody, user);

        InOutTransaction transaction =
                buildTransaction(
                        requestBody, user, account, category, customCategory, tags, customTags, type);

        Map<String, Account> accounts = new HashMap<>();
        if (type == TransactionType.INCOME) {
            accounts.put(ACCOUNT_TO, account);
        } else {
            accounts.put(ACCOUNT_FROM, account);
        }

        updateBalance(requestBody.getAmount(), accounts);

        log.info(format(IN_OUT_TRANSACTION_CREATED_MSG, user.getUsername(), buildResponseModel(transaction)));
        return buildResponseModel(transaction);
    }

    @Override
    @Transactional
    public TransferResponseModel createTransaction(TransferRequestModel requestBody,
                                                   TransactionType transactionType,
                                                   String username) {
        CustomValidator.validateTransferModel(requestBody);

        User user = userByUsername(username);
        Account accountFrom = accountById(requestBody.getAccountFromId(), user);
        Account accountTo = accountById(requestBody.getAccountToId(), user);

        if (!accountFrom.isAllowNegative() && accountFrom.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, accountFrom.getName()));
        }

        Map<String, Account> accounts = new HashMap<>() {{
            put(ACCOUNT_FROM, accountFrom);
            put(ACCOUNT_TO, accountTo);
        }};

        TransferTransaction transaction = buildTransaction(requestBody, user, accountFrom, accountTo);
        updateBalance(requestBody.getAmount(), accounts);

        log.info(format(TRANSFER_TRANSACTION_CREATED_MSG, user.getUsername(), buildResponseModel(transaction)));
        return buildResponseModel(transaction);
    }

    @Override
    @Transactional
    public DebtResponseModel createTransaction(DebtRequestModel requestBody,
                                               TransactionType type,
                                               String username) {
        CustomValidator.validateDebtModel(requestBody);
        User user = userByUsername(username);

        Account account = accountById(requestBody.getAccountId(), user);

        if (type.equals(TransactionType.DEBT_OUT)
                && !account.isAllowNegative()
                && account.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }

        Map<String, Account> accounts = new HashMap<>();
        if (type == TransactionType.DEBT_IN) {
            accounts.put(ACCOUNT_TO, account);
        } else {
            accounts.put(ACCOUNT_FROM, account);
        }

        DebtTransaction transaction = buildTransaction(requestBody, type, user, account);
        updateBalance(requestBody.getAmount(), accounts);

        log.info(format(DEBT_TRANSACTION_CREATED_MSG, user.getUsername(), buildResponseModel(transaction)));
        return buildResponseModel(transaction);
    }

    @Override
    @Transactional
    public InOutResponseModel updateTransaction(UpdateInOutRequestModel requestBody, String username) {
        CustomValidator.validateUpdateInOutModel(requestBody);

        User user = userByUsername(username);
        InOutTransaction transaction = inOutTransactionById(requestBody.getTransactionId(), user);
        Account account = accountById(requestBody.getAccountId(), user);

        if (transaction.getTransactionType().equals(TransactionType.OUTCOME.name())
                && !account.isAllowNegative()
                && account.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }

        Optional<Category> category =
                categoryByIdAndType(requestBody, TransactionType.valueOf(transaction.getTransactionType()));

        Optional<CustomCategory> customCategory =
                customCategoryByIdAndType(requestBody, TransactionType.valueOf(transaction.getTransactionType()), user);

        checkCategory(requestBody, category, customCategory);

        List<Tag> tags = tagsByIds(requestBody);
        List<CustomTag> customTags = customTagsByIds(requestBody, user);

        Map<String, Account> newAccounts = new HashMap<>();
        Map<String, Account> oldAccounts = new HashMap<>();
        BigDecimal oldAmount = transaction.getAmount();

        if (transaction.getTransactionType().equals(TransactionType.OUTCOME.name())) {
            oldAccounts.put(ACCOUNT_TO, transaction.getAccount());
            newAccounts.put(ACCOUNT_FROM, account);
        } else {
            oldAccounts.put(ACCOUNT_FROM, transaction.getAccount());
            newAccounts.put(ACCOUNT_TO, account);
        }

        InOutTransaction updatedTransaction =
                updateTransactionValues(requestBody, transaction, account, category, customCategory, tags, customTags);
        updateBalance(oldAmount, oldAccounts);
        updateBalance(requestBody.getAmount(), newAccounts);

        log.info(format(IN_OUT_TRANSACTION_UPDATED_MSG, user.getUsername(), buildResponseModel(updatedTransaction)));
        return buildResponseModel(updatedTransaction);
    }

    @Override
    @Transactional
    public TransferResponseModel updateTransaction(UpdateTransferRequestModel requestBody, String username) {
        CustomValidator.validateUpdateTransferModel(requestBody);

        User user = userByUsername(username);
        TransferTransaction transaction = transferTransactionById(requestBody.getTransactionId(), user);
        Account accountFrom = accountById(requestBody.getAccountFromId(), user);
        Account accountTo = accountById(requestBody.getAccountToId(), user);
        BigDecimal oldAmount = transaction.getAmount();

        if (requestBody.getAccountFromId().equals(transaction.getAccountFrom().getAccountId())) {
            if (!accountFrom.isAllowNegative()
                    && ((accountFrom.getBalance().add(oldAmount)).compareTo(requestBody.getAmount()) < 0)) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, accountFrom.getName()));
            }
        } else if (requestBody.getAccountFromId().equals(transaction.getAccountTo().getAccountId())) {
            if (!accountFrom.isAllowNegative()
                    && (accountFrom.getBalance().subtract(oldAmount)).compareTo(requestBody.getAmount()) < 0) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, accountFrom.getName()));
            }
        } else {
            if (!accountFrom.isAllowNegative()
                    && accountFrom.getBalance().compareTo(requestBody.getAmount()) < 0) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, accountFrom.getName()));
            }

        }


        Map<String, Account> oldAccounts = new HashMap<>();
        Map<String, Account> newAccounts = new HashMap<>();

        oldAccounts.put(ACCOUNT_FROM, transaction.getAccountTo());
        oldAccounts.put(ACCOUNT_TO, transaction.getAccountFrom());
        newAccounts.put(ACCOUNT_FROM, accountFrom);
        newAccounts.put(ACCOUNT_TO, accountTo);

        TransferTransaction updatedTransaction =
                updateTransactionValues(requestBody, transaction, accountFrom, accountTo);

        updateBalance(oldAmount, oldAccounts);

        updateBalance(requestBody.getAmount(), newAccounts);

        log.info(format(TRANSFER_TRANSACTION_UPDATED_MSG, user.getUsername(), buildResponseModel(updatedTransaction)));
        return buildResponseModel(updatedTransaction);
    }

    @Override
    @Transactional
    public DebtResponseModel updateTransaction(UpdateDebtRequestModel requestBody, String username) {
        CustomValidator.validateUpdateDebtModel(requestBody);

        User user = userByUsername(username);
        Account account = accountById(requestBody.getAccountId(), user);
        DebtTransaction transaction = debtTransactionById(requestBody.getTransactionId(), user);
        BigDecimal oldAmount = transaction.getAmount();


        if (transaction.getTransactionType().equals(TransactionType.DEBT_OUT.name())) {
            if (requestBody.getAccountId().equals(transaction.getAccount().getAccountId())
                    && !account.isAllowNegative()
                    && ((account.getBalance().add(oldAmount)).compareTo(requestBody.getAmount()) < 0)) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
            } else if (!requestBody.getAccountId().equals(transaction.getAccount().getAccountId())
                    && !account.isAllowNegative()
                    && account.getBalance().compareTo(requestBody.getAmount()) < 0) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
            }
        }

        Map<String, Account> oldAccounts = new HashMap<>();
        Map<String, Account> newAccounts = new HashMap<>();

        if (transaction.getTransactionType().equals(TransactionType.DEBT_OUT.name())) {
            oldAccounts.put(ACCOUNT_TO, transaction.getAccount());
            newAccounts.put(ACCOUNT_FROM, account);
        } else {
            oldAccounts.put(ACCOUNT_FROM, transaction.getAccount());
            newAccounts.put(ACCOUNT_TO, account);
        }

        DebtTransaction updatedTransaction = updateTransactionValues(requestBody, account, transaction);

        updateBalance(oldAmount, oldAccounts);
        updateBalance(requestBody.getAmount(), newAccounts);

        log.info(format(DEBT_TRANSACTION_UPDATED_MSG, user.getUsername(), buildResponseModel(updatedTransaction)));
        return buildResponseModel(updatedTransaction);
    }

    @Override
    public List<TransactionResponseModel> getAllTransactionsByUser(String username) {
        User user = userByUsername(username);

        List<InOutTransaction> inOutList = inOutRepo.allByUser(user);
        List<TransferTransaction> transferList = transferRepo.allByUser(user);
        List<DebtTransaction> debtList = debtRepo.allByUser(user);

        List<TransactionResponseModel> transactions = new ArrayList<>();

        inOutList
                .stream()
                .map(this::buildGenericResponseModel)
                .forEach(transactions::add);

        transferList
                .stream()
                .map(this::buildGenericResponseModel)
                .forEach(transactions::add);

        debtList
                .stream()
                .map(this::buildGenericResponseModel)
                .forEach(transactions::add);

        if (transactions.isEmpty()) {
            throw new NoExistingTransactionException(format(NO_EXISTING_TRANSACTION_MSG, username));
        }

        log.info(String.format(ALL_TRANSACTIONS_MSG, user.getUsername(), transactions));
        return transactions;

    }

    private InOutTransaction buildTransaction(InOutRequestModel requestBody, User user,
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

    private TransferTransaction buildTransaction(TransferRequestModel requestBody, User user,
                                                 Account accountFrom,
                                                 Account accountTo) {
        return transferRepo.save(TransferTransaction.builder()
                .transferTransactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.TRANSFER.name())
                .creationDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getCreationDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .user(user)
                .build());
    }

    private DebtTransaction buildTransaction(DebtRequestModel requestBody, TransactionType type, User user,
                                             Account account) {
        return debtRepo.save(DebtTransaction.builder()
                .debtTransactionId(UUID.randomUUID().toString())
                .creationDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getCreationDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .transactionType(type.name())
                .account(account)
                .user(user)
                .build());
    }

    private InOutTransaction updateTransactionValues(UpdateInOutRequestModel requestBody, InOutTransaction transaction,
                                                     Account account, Optional<Category> category,
                                                     Optional<CustomCategory> customCategory,
                                                     List<Tag> tags, List<CustomTag> customTags) {
        transaction.setCreationDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getCreationDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        transaction.setAccount(account);
        transaction.setTags(tags);
        transaction.setCustomTags(customTags);
        category.ifPresent(transaction::setCategory);
        customCategory.ifPresent(transaction::setCustomCategory);
        return inOutRepo.save(transaction);
    }

    private TransferTransaction updateTransactionValues(UpdateTransferRequestModel requestBody,
                                                        TransferTransaction transaction,
                                                        Account accountFrom,
                                                        Account accountTo) {
        transaction.setCreationDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getCreationDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        return transferRepo.save(transaction);
    }

    private DebtTransaction updateTransactionValues(UpdateDebtRequestModel requestBody, Account account,
                                                    DebtTransaction transaction) {
        transaction.setCreationDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getCreationDateTime()));
        transaction.setAccount(account);
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        return debtRepo.save(transaction);
    }

    private InOutResponseModel buildResponseModel(InOutTransaction transaction) {
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

    private TransferResponseModel buildResponseModel(TransferTransaction transaction) {
        return TransferResponseModel.builder()
                .transactionId(transaction.getTransferTransactionId())
                .creationDateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .accountFrom(transaction.getAccountFrom().getAccountId())
                .accountTo(transaction.getAccountTo().getAccountId())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    private DebtResponseModel buildResponseModel(DebtTransaction transaction) {
        return DebtResponseModel.builder()
                .transactionId(transaction.getDebtTransactionId())
                .creationDateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .accountId(transaction.getAccount().getAccountId())
                .build();
    }

    private TransactionResponseModel buildGenericResponseModel(InOutTransaction transaction) {
        TransactionResponseModel model = TransactionResponseModel.builder()
                .transactionId(transaction.getInOutTransactionId())
                .creationDateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .accountId(transaction.getAccount().getAccountId())
                .transactionType(transaction.getTransactionType())
                .build();

        String categoryId = transaction.getCategory() == null
                ? transaction.getCustomCategory().getCustomCategoryId()
                : transaction.getCategory().getCategoryId();

        List<String> tags = transaction.getTags().stream().map(Tag::getTagId).collect(Collectors.toList());
        transaction.getCustomTags().stream().map(CustomTag::getCustomTagId).forEach(tags::add);

        model.setCategoryId(categoryId);
        model.setTagIds(tags);

        return model;
    }

    private TransactionResponseModel buildGenericResponseModel(TransferTransaction transaction) {
        return TransactionResponseModel.builder()
                .transactionId(transaction.getTransferTransactionId())
                .creationDateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .accountId(transaction.getAccountFrom().getAccountId())
                .oppositeAccountId(transaction.getAccountTo().getAccountId())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    private TransactionResponseModel buildGenericResponseModel(DebtTransaction transaction) {
        return TransactionResponseModel.builder()
                .transactionId(transaction.getDebtTransactionId())
                .creationDateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .accountId(transaction.getAccount().getAccountId())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    private User userByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MSG, username)));
    }

    private Account accountById(String accountId, User user) {
        return accountRepo.byIdAndUser(accountId, user)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                format(UNAUTHORIZED_ACCOUNT_MSG, user.getUsername(), accountId)));
    }

    private InOutTransaction inOutTransactionById(String transactionId, User user) {
        return inOutRepo.byIdAndUser(transactionId, user)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionId)));
    }

    private TransferTransaction transferTransactionById(String transactionId, User user) {
        return transferRepo.byIdAndUser(transactionId, user)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionId)));
    }

    private DebtTransaction debtTransactionById(String transactionId, User user) {
        return debtRepo.byIdAndUser(transactionId, user)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionId)));
    }

    private Optional<Category> categoryByIdAndType(InOutRequestModel requestBody, TransactionType type) {
        return categoryRepo
                .byIdAndType(requestBody.getCategoryId(), CategoryType.valueOf(type.name()).name());
    }

    private Optional<CustomCategory> customCategoryByIdAndType(InOutRequestModel requestBody,
                                                               TransactionType type,
                                                               User user) {
        return customCategoryRepo
                .byIdAndUserAndType(
                        requestBody.getCategoryId(),
                        user,
                        CategoryType.valueOf(type.name()).name());
    }

    private List<Tag> tagsByIds(InOutRequestModel requestBody) {
        return requestBody.getTagIds()
                .stream()
                .filter(id -> tagRepository.byId(id).isPresent())
                .map(id -> tagRepository.byId(id).get())
                .collect(Collectors.toList());
    }

    private List<CustomTag> customTagsByIds(InOutRequestModel requestBody, User user) {
        return requestBody.getTagIds()
                .stream()
                .filter(id -> customTagRepo.byIdAndUser(id, user).isPresent())
                .map(id -> customTagRepo.byIdAndUser(id, user).get())
                .collect(Collectors.toList());
    }

    private void updateBalance(BigDecimal amount, Map<String, Account> accounts) {

        if (!Objects.isNull(accounts.get(ACCOUNT_FROM))) {
            accounts.get(ACCOUNT_FROM).setBalance(accounts.get(ACCOUNT_FROM).getBalance().subtract(amount));
            accountRepo.save(accounts.get(ACCOUNT_FROM));
        }

        if (!Objects.isNull(accounts.get(ACCOUNT_TO))) {
            accounts.get(ACCOUNT_TO).setBalance(accounts.get(ACCOUNT_TO).getBalance().add(amount));
            accountRepo.save(accounts.get(ACCOUNT_TO));
        }
    }

    private void checkCategory(InOutRequestModel requestBody,
                               Optional<Category> category,
                               Optional<CustomCategory> customCategory) {
        if (category.isEmpty() && customCategory.isEmpty()) {
            throw new CategoryNotFoundException(format(INVALID_CATEGORY_ID_MSG, requestBody.getCategoryId()));
        }
    }
}
