package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.COMMON_USERNAME;
import static com.budgetmanagementapp.utility.Constant.RECEIVER_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.SENDER_ACCOUNT;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_TRANSACTIONS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INCOME_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INSUFFICIENT_BALANCE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_CATEGORY_ID_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_TRANSACTION_TYPE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.NO_EXISTING_TRANSACTION_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_ACCOUNT_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_TRANSACTION_MSG;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_OUT;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.OUTGOING;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static com.budgetmanagementapp.utility.TransactionType.valueOf;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.AccountNotFoundException;
import com.budgetmanagementapp.exception.CategoryNotFoundException;
import com.budgetmanagementapp.exception.InvalidTransactionTypeException;
import com.budgetmanagementapp.exception.NoExistingTransactionException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.TransactionNotFoundException;
import com.budgetmanagementapp.model.DebtRequestModel;
import com.budgetmanagementapp.model.DebtResponseModel;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.model.TransactionRequestModel;
import com.budgetmanagementapp.model.TransactionResponseModel;
import com.budgetmanagementapp.model.TransferRequestModel;
import com.budgetmanagementapp.model.TransferResponseModel;
import com.budgetmanagementapp.model.UpdateDebtRequestModel;
import com.budgetmanagementapp.model.UpdateTransactionRequestModel;
import com.budgetmanagementapp.model.UpdateTransferRequestModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.TransactionRepository;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.TransactionType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private final UserService userService;
    private final AccountRepository accountRepo;
    private final CategoryRepository categoryRepo;
    private final TagRepository tagRepository;
    private final TransactionRepository transactionRepo;

    @Override
    @Transactional
    public TransactionResponseModel createInOutTransaction(InOutRequestModel requestBody,
                                                           TransactionType type,
                                                           String username) {
        User user = userService.findByUsername(username);
        Account account = accountById(requestBody.getAccountId(), user);
        Category category = categoryByIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Tag> tags = tagsByIdsAndTypeAndUser(requestBody.getTagIds(), type.name(), user);

        checkBalanceAvailability(requestBody.getAmount(), type, account);

        Map<String, Account> accounts = type.equals(OUTGOING)
                ? Collections.singletonMap(SENDER_ACCOUNT, account)
                : Collections.singletonMap(RECEIVER_ACCOUNT, account);

        Transaction transaction = buildTransaction(requestBody, user, account, category, tags, type);
        updateBalance(requestBody.getAmount(), accounts);

        InOutResponseModel response = buildInOutResponseModel(transaction);
        log.info(format(INCOME_TRANSACTION_CREATED_MSG, user.getUsername(), response));
        return response;


    }

    @Transactional
    public TransferResponseModel createTransferTransaction(TransferRequestModel requestBody,
                                                           TransactionType transactionType,
                                                           String username) {
        User user = userService.findByUsername(username);
        Account senderAccount = accountById(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountById(requestBody.getReceiverAccountId(), user);

        if (!senderAccount.isAllowNegative() && senderAccount.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, senderAccount.getName()));
        }

        Map<String, Account> accounts = new HashMap<>() {{
            put(SENDER_ACCOUNT, senderAccount);
            put(RECEIVER_ACCOUNT, receiverAccount);
        }};

        Transaction transaction = buildTransaction(requestBody, user, senderAccount, receiverAccount);
        updateBalance(requestBody.getAmount(), accounts);

        TransferResponseModel response = buildTransferResponseModel(transaction);
        log.info(format(TRANSFER_TRANSACTION_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    @Transactional
    public DebtResponseModel createDebtTransaction(DebtRequestModel requestBody,
                                                   TransactionType type,
                                                   String username) {
        User user = userService.findByUsername(username);
        Account account = accountById(requestBody.getAccountId(), user);

        checkBalanceAvailability(requestBody.getAmount(), type, account);

        Map<String, Account> accounts = type.equals(DEBT_OUT)
                ? Collections.singletonMap(SENDER_ACCOUNT, account)
                : Collections.singletonMap(RECEIVER_ACCOUNT, account);

        Transaction transaction = buildTransaction(requestBody, type, user, account);
        updateBalance(requestBody.getAmount(), accounts);

        DebtResponseModel response = buildDebtResponseModel(transaction);
        log.info(format(DEBT_TRANSACTION_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    private void checkBalanceAvailability(BigDecimal amount, TransactionType type, Account account) {
        if (type.equals(DEBT_OUT) || type.equals(OUTGOING)
                && !account.isAllowNegative()
                && account.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }
    }

    @Override
    @Transactional
    public InOutResponseModel updateTransaction(UpdateTransactionRequestModel requestBody, String username) {
        CustomValidator.validateUpdateInOutModel(requestBody);

        User user = userByUsername(username);
        Transaction transaction = inOutTransactionById(requestBody.getTransactionId(), user);
        Account account = accountById(requestBody.getAccountId(), user);

        if (transaction.getTransactionType().equals(OUTGOING.name())
                && !account.isAllowNegative()
                && account.getBalance().compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }

        Optional<Category> category =
                categoryByIdAndTypeAndUser(requestBody, valueOf(transaction.getTransactionType()));

        Optional<Category> customCategory =
                customCategoryByIdAndType(requestBody, valueOf(transaction.getTransactionType()), user);

        checkCategory(requestBody, category, customCategory);

        List<Tag> tags = tagsByIdsAndTypeAndUser(requestBody);
        List<Tag> customTags = customTagsByIds(requestBody, user);

        Map<String, Account> newAccounts = new HashMap<>();
        Map<String, Account> oldAccounts = new HashMap<>();
        BigDecimal oldAmount = transaction.getAmount();

        if (transaction.getTransactionType().equals(OUTGOING.name())) {
            oldAccounts.put(RECEIVER_ACCOUNT, transaction.getSenderAccount());
            newAccounts.put(SENDER_ACCOUNT, account);
        } else {
            oldAccounts.put(SENDER_ACCOUNT, transaction.getSenderAccount());
            newAccounts.put(RECEIVER_ACCOUNT, account);
        }

        Transaction updatedTransaction =
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

        oldAccounts.put(SENDER_ACCOUNT, transaction.getAccountTo());
        oldAccounts.put(RECEIVER_ACCOUNT, transaction.getAccountFrom());
        newAccounts.put(SENDER_ACCOUNT, accountFrom);
        newAccounts.put(RECEIVER_ACCOUNT, accountTo);

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


        if (transaction.getTransactionType().equals(DEBT_OUT.name())) {
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

        if (transaction.getTransactionType().equals(DEBT_OUT.name())) {
            oldAccounts.put(RECEIVER_ACCOUNT, transaction.getAccount());
            newAccounts.put(SENDER_ACCOUNT, account);
        } else {
            oldAccounts.put(SENDER_ACCOUNT, transaction.getAccount());
            newAccounts.put(RECEIVER_ACCOUNT, account);
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

        List<Transaction> inOutList = transactionRepo.allByUser(user);
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

    private Transaction buildTransaction(TransactionRequestModel requestBody, User user,
                                         Account account, Category category,
                                         List<Tag> tags, TransactionType type) {
        Transaction transaction = transactionRepo.save(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .transactionType(type.name())
                .category(category)
                .tags(tags)
                .user(user)
                .build());

        if (type.equals(INCOME)) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }

        return transaction;
    }

    private Transaction buildTransaction(TransferRequestModel requestBody, User user,
                                         Account senderAccount,
                                         Account receiverAccount) {
        return transactionRepo.save(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType(TRANSFER.name())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .user(user)
                .build());
    }

    private Transaction buildTransaction(DebtRequestModel requestBody, TransactionType type,
                                         User user,
                                         Account account) {
        Transaction transaction = transactionRepo.save(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .transactionType(type.name())
                .user(user)
                .build());

        if (type.equals(DEBT_IN)) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }

        return transaction;
    }

    private Transaction updateTransactionValues(UpdateTransactionRequestModel requestBody, Transaction transaction,
                                                Account account, Optional<Category> category,
                                                Optional<Category> customCategory,
                                                List<Tag> tags, List<Tag> customTags) {
        transaction.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        transaction.setSenderAccount(account);
        transaction.setTags(tags);
        transaction.setTags(customTags);
        category.ifPresent(transaction::setCategory);
        customCategory.ifPresent(transaction::setCategory);
        return transactionRepo.save(transaction);
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

    private TransactionResponseModel buildResponseModel(Transaction transaction) {
        switch (valueOf(transaction.getTransactionType())) {
            case INCOME:
            case OUTGOING:
                return buildInOutResponseModel(transaction);

            case TRANSFER:
                return buildTransferResponseModel(transaction);

            case DEBT_IN:
            case DEBT_OUT:
                return buildDebtResponseModel(transaction);

            default:
                throw new InvalidTransactionTypeException(
                        format(INVALID_TRANSACTION_TYPE_MSG, transaction.getTransactionType()));
        }
    }

    private TransactionResponseModel buildGenericResponseModel(Transaction transaction) {
        return TransactionResponseModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    private DebtResponseModel buildDebtResponseModel(Transaction transaction) {
        return DebtResponseModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .accountId(
                        transaction.getTransactionType().equals(DEBT_IN.name())
                                ? transaction.getReceiverAccount().getAccountId()
                                : transaction.getSenderAccount().getAccountId())
                .build();
    }

    private TransferResponseModel buildTransferResponseModel(Transaction transaction) {
        return TransferResponseModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .senderAccountId(transaction.getSenderAccount().getAccountId())
                .receiverAccountId(transaction.getReceiverAccount().getAccountId())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    private InOutResponseModel buildInOutResponseModel(Transaction transaction) {
        return InOutResponseModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .accountId(
                        transaction.getTransactionType().equals(INCOME.name())
                                ? transaction.getReceiverAccount().getAccountId()
                                : transaction.getSenderAccount().getAccountId())
                .categoryId(transaction.getCategory().getCategoryId())
                .tagIds(transaction.getTags().stream().map(Tag::getTagId).collect(Collectors.toList()))
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


    private TransactionResponseModel buildGenericResponseModel(TransferTransaction transaction) {
        return TransactionResponseModel.builder()
                .transactionId(transaction.getTransferTransactionId())
                .dateTime(transaction.getCreationDateTime())
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
                .dateTime(transaction.getCreationDateTime())
                .amount(transaction.getAmount())
                .accountId(transaction.getAccount().getAccountId())
                .description(transaction.getDescription())
                .transactionType(transaction.getTransactionType())
                .build();
    }

    private Account accountById(String accountId, User user) {
        return accountRepo.byIdAndUser(accountId, user)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                format(UNAUTHORIZED_ACCOUNT_MSG, user.getUsername(), accountId)));
    }

    private Transaction inOutTransactionById(String transactionId, User user) {
        return transactionRepo.byIdAndUser(transactionId, user)
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

    private Category categoryByIdAndTypeAndUser(String categoryId, TransactionType type, User user) {
        return categoryRepo
                .byIdAndTypeAndUsers(
                        categoryId,
                        CategoryType.valueOf(type.name()).name(),
                        Arrays.asList(user, userService.findByUsername(COMMON_USERNAME)))
                .orElseThrow(() -> new CategoryNotFoundException(format(INVALID_CATEGORY_ID_MSG, categoryId)));
    }

    private List<Tag> tagsByIdsAndTypeAndUser(List<String> tagIds, String type, User user) {
        return tagIds
                .stream()
                .filter(id -> tagByIdAndTypeAndUser(id, type, user).isPresent())
                .map(id -> tagByIdAndTypeAndUser(id, type, user).get())
                .collect(Collectors.toList());
    }

    private Optional<Tag> tagByIdAndTypeAndUser(String tagId, String type, User user) {
        return tagRepository.byIdAndTypeAndUsers(
                tagId,
                type,
                Arrays.asList(user, userService.findByUsername(COMMON_USERNAME)));
    }

    private void updateBalance(BigDecimal amount, Map<String, Account> accounts) {

        if (!Objects.isNull(accounts.get(SENDER_ACCOUNT))) {
            accounts.get(SENDER_ACCOUNT).setBalance(accounts.get(SENDER_ACCOUNT).getBalance().subtract(amount));
            accountRepo.save(accounts.get(SENDER_ACCOUNT));
        }

        if (!Objects.isNull(accounts.get(RECEIVER_ACCOUNT))) {
            accounts.get(RECEIVER_ACCOUNT).setBalance(accounts.get(RECEIVER_ACCOUNT).getBalance().add(amount));
            accountRepo.save(accounts.get(RECEIVER_ACCOUNT));
        }
    }

}
