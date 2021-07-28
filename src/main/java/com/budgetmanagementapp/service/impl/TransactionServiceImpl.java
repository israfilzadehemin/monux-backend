package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.RECEIVER_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.SENDER_ACCOUNT;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_OUT;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.OUTGOING;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static com.budgetmanagementapp.utility.TransactionType.valueOf;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.TransactionNotFoundException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.model.DebtRqModel;
import com.budgetmanagementapp.model.DebtRsModel;
import com.budgetmanagementapp.model.InOutRqModel;
import com.budgetmanagementapp.model.InOutRsModel;
import com.budgetmanagementapp.model.TransactionRsModel;
import com.budgetmanagementapp.model.TransferRqModel;
import com.budgetmanagementapp.model.TransferRsModel;
import com.budgetmanagementapp.model.UpdateDebtRqModel;
import com.budgetmanagementapp.model.UpdateInOutRqModel;
import com.budgetmanagementapp.model.UpdateTransferRqModel;
import com.budgetmanagementapp.repository.TransactionRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.CategoryService;
import com.budgetmanagementapp.service.LabelService;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.PaginationTool;
import com.budgetmanagementapp.utility.TransactionType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserService userService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final LabelService labelService;
    private final TransactionRepository transactionRepo;
    private final PaginationTool<Transaction> paginationTool;

    @Override
    @Transactional
    public TransactionRsModel createTransaction(InOutRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category = categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), type.name(), user);

        checkBalanceToCreateTransaction(requestBody.getAmount(), type, account);

        Map<String, Account> accounts = type.equals(OUTGOING)
                ? Collections.singletonMap(SENDER_ACCOUNT, account)
                : Collections.singletonMap(RECEIVER_ACCOUNT, account);

        Transaction transaction = buildTransaction(requestBody, user, account, category, labels, type);
        accountService.updateBalance(requestBody.getAmount(), accounts);

        InOutRsModel response = buildInOutResponseModel(transaction);
        log.info(format(IN_OUT_TRANSACTION_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    @Transactional
    public TransferRsModel createTransaction(TransferRqModel requestBody,
                                             TransactionType transactionType,
                                             String username) {
        User user = userService.findByUsername(username);
        Account senderAccount = accountService.byIdAndUser(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountService.byIdAndUser(requestBody.getReceiverAccountId(), user);

        if (requestBody.getReceiverAccountId().equals(requestBody.getSenderAccountId())) {
            throw new TransferToSelfException(TRANSFER_TO_SELF_MSG);
        }

        checkBalanceToCreateTransaction(requestBody.getAmount(), transactionType, senderAccount);

        Map<String, Account> accounts = new HashMap<>() {{
            put(SENDER_ACCOUNT, senderAccount);
            put(RECEIVER_ACCOUNT, receiverAccount);
        }};

        Transaction transaction = buildTransaction(requestBody, user, senderAccount, receiverAccount);
        accountService.updateBalance(requestBody.getAmount(), accounts);

        TransferRsModel response = buildTransferResponseModel(transaction);
        log.info(format(TRANSFER_TRANSACTION_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    @Transactional
    public DebtRsModel createTransaction(DebtRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);

        checkBalanceToCreateTransaction(requestBody.getAmount(), type, account);

        Map<String, Account> accounts = type.equals(DEBT_OUT)
                ? Collections.singletonMap(SENDER_ACCOUNT, account)
                : Collections.singletonMap(RECEIVER_ACCOUNT, account);

        Transaction transaction = buildTransaction(requestBody, type, user, account);
        accountService.updateBalance(requestBody.getAmount(), accounts);

        DebtRsModel response = buildDebtResponseModel(transaction);
        log.info(format(DEBT_TRANSACTION_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    @Transactional
    public InOutRsModel updateTransaction(UpdateInOutRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Transaction transaction = transactionByIdAndUser(requestBody.getTransactionId(), user);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category =
                categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), valueOf(transaction.getType()), user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), transaction.getType(), user);
        BigDecimal oldAmount = transaction.getAmount();

        checkBalanceToUpdateInOut(requestBody, transaction, account, oldAmount);

        Map<String, Account> newAccounts = new HashMap<>();
        Map<String, Account> oldAccounts = new HashMap<>();

        if (transaction.getType().equals(OUTGOING.name())) {
            oldAccounts.put(RECEIVER_ACCOUNT, transaction.getSenderAccount());
            newAccounts.put(SENDER_ACCOUNT, account);
        } else {
            oldAccounts.put(SENDER_ACCOUNT, transaction.getReceiverAccount());
            newAccounts.put(RECEIVER_ACCOUNT, account);
        }

        Transaction updatedTransaction = updateTransactionValues(requestBody, transaction, account, category, labels);
        accountService.updateBalance(oldAmount, oldAccounts);
        accountService.updateBalance(requestBody.getAmount(), newAccounts);

        InOutRsModel response = buildInOutResponseModel(updatedTransaction);
        log.info(format(IN_OUT_TRANSACTION_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    @Transactional
    public TransferRsModel updateTransaction(UpdateTransferRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Transaction transaction = transactionByIdAndUser(requestBody.getTransactionId(), user);
        Account senderAccount = accountService.byIdAndUser(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountService.byIdAndUser(requestBody.getReceiverAccountId(), user);
        BigDecimal oldAmount = transaction.getAmount();

        if (requestBody.getReceiverAccountId().equals(requestBody.getSenderAccountId())) {
            throw new TransferToSelfException(TRANSFER_TO_SELF_MSG);
        }

        checkBalanceToUpdateTransfer(requestBody, transaction, senderAccount, oldAmount);

        Map<String, Account> oldAccounts = new HashMap<>();
        Map<String, Account> newAccounts = new HashMap<>();

        oldAccounts.put(SENDER_ACCOUNT, transaction.getReceiverAccount());
        oldAccounts.put(RECEIVER_ACCOUNT, transaction.getSenderAccount());
        newAccounts.put(SENDER_ACCOUNT, senderAccount);
        newAccounts.put(RECEIVER_ACCOUNT, receiverAccount);

        Transaction updatedTransaction =
                updateTransactionValues(requestBody, transaction, senderAccount, receiverAccount);
        accountService.updateBalance(oldAmount, oldAccounts);
        accountService.updateBalance(requestBody.getAmount(), newAccounts);

        TransferRsModel response = buildTransferResponseModel(updatedTransaction);
        log.info(format(TRANSFER_TRANSACTION_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    @Transactional
    public DebtRsModel updateTransaction(UpdateDebtRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Transaction transaction = transactionByIdAndUser(requestBody.getTransactionId(), user);
        BigDecimal oldAmount = transaction.getAmount();

        checkBalanceToUpdateDebt(requestBody, account, transaction, oldAmount);

        Map<String, Account> oldAccounts = new HashMap<>();
        Map<String, Account> newAccounts = new HashMap<>();

        if (transaction.getType().equals(DEBT_OUT.name())) {
            oldAccounts.put(RECEIVER_ACCOUNT, transaction.getSenderAccount());
            newAccounts.put(SENDER_ACCOUNT, account);
        } else {
            oldAccounts.put(SENDER_ACCOUNT, transaction.getReceiverAccount());
            newAccounts.put(RECEIVER_ACCOUNT, account);
        }

        Transaction updatedTransaction = updateTransactionValues(requestBody, account, transaction);
        accountService.updateBalance(oldAmount, oldAccounts);
        accountService.updateBalance(requestBody.getAmount(), newAccounts);

        DebtRsModel response = buildDebtResponseModel(updatedTransaction);
        log.info(format(DEBT_TRANSACTION_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public List<TransactionRsModel> getAllTransactionsByUser(String username) {
        List<TransactionRsModel> response =
                transactionRepo.allByUser(userService.findByUsername(username))
                        .stream()
                        .map(this::buildGenericResponseModel)
                        .collect(Collectors.toList());

        log.info(String.format(ALL_TRANSACTIONS_MSG, username, response));
        return response;
    }

    @Override
    public List<TransactionRsModel> getLastTransactionsByUser(String username, int pageCount, int size, String sortField, String sortDir) {
        Pageable pageable = paginationTool.service(pageCount, size, sortField, sortDir);

        User user = userService.findByUsername(username);
        Page<Transaction> allPosts = transactionRepo.lastByUser(user, pageable);

        if (allPosts.getTotalElements() == 0) {
            throw new TransactionNotFoundException(TRANSACTION_TYPE_NOT_FOUND_MSG);
        } else {
            List<TransactionRsModel> response =
                    transactionRepo.lastByUser(user, pageable)
                            .stream()
                            .map(this::buildGenericResponseModel)
                            .collect(Collectors.toList());

            log.info(format(LAST_TRANSACTIONS_MSG, username, response));
            return response;
        }
    }


    private Transaction buildTransaction(InOutRqModel requestBody, User user,
                                         Account account, Category category,
                                         List<Label> labels, TransactionType type) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .category(category)
                .labels(labels)
                .user(user)
                .build();

        if (type.equals(INCOME)) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }
        return transactionRepo.save(transaction);
    }

    private Transaction buildTransaction(TransferRqModel requestBody, User user,
                                         Account senderAccount,
                                         Account receiverAccount) {
        return transactionRepo.save(Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .type(TRANSFER.name())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .user(user)
                .build());
    }

    private Transaction buildTransaction(DebtRqModel requestBody,
                                         TransactionType type,
                                         User user,
                                         Account account) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .user(user)
                .build();

        if (type.equals(DEBT_IN)) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }

        return transactionRepo.save(transaction);
    }

    private Transaction updateTransactionValues(UpdateInOutRqModel requestBody, Transaction transaction,
                                                Account account, Category category,
                                                List<Label> labels) {
        transaction.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        transaction.setCategory(category);
        transaction.setLabels(labels);

        if (transaction.getType().equals(INCOME.name())) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }

        return transactionRepo.save(transaction);
    }

    private Transaction updateTransactionValues(UpdateTransferRqModel requestBody,
                                                Transaction transaction,
                                                Account senderAccount,
                                                Account receiverAccount) {
        transaction.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        return transactionRepo.save(transaction);
    }

    private Transaction updateTransactionValues(UpdateDebtRqModel requestBody,
                                                Account account,
                                                Transaction transaction) {
        transaction.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());

        if (transaction.getType().equals(DEBT_IN.name())) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }

        return transactionRepo.save(transaction);
    }

    private TransactionRsModel buildGenericResponseModel(Transaction transaction) {
        TransactionRsModel response = TransactionRsModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .type(transaction.getType())
                .build();

        if (!Objects.isNull(transaction.getSenderAccount())) {
            response.setSenderAccountId(transaction.getSenderAccount().getAccountId());
        }
        if (!Objects.isNull(transaction.getReceiverAccount())) {
            response.setReceiverAccountId(transaction.getReceiverAccount().getAccountId());
        }
        if (!Objects.isNull(transaction.getCategory())) {
            response.setCategoryId(transaction.getCategory().getCategoryId());
        }
        if (!Objects.isNull(transaction.getLabels())) {
            response.setLabelIds(transaction.getLabels().stream().map(Label::getLabelId).collect(Collectors.toList()));
        }

        return response;
    }

    private InOutRsModel buildInOutResponseModel(Transaction transaction) {
        return InOutRsModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .type(transaction.getType())
                .accountId(
                        transaction.getType().equals(INCOME.name())
                                ? transaction.getReceiverAccount().getAccountId()
                                : transaction.getSenderAccount().getAccountId())
                .categoryId(transaction.getCategory().getCategoryId())
                .labelIds(transaction.getLabels().stream().map(Label::getLabelId).collect(Collectors.toList()))
                .build();
    }

    private TransferRsModel buildTransferResponseModel(Transaction transaction) {
        return TransferRsModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .senderAccountId(transaction.getSenderAccount().getAccountId())
                .receiverAccountId(transaction.getReceiverAccount().getAccountId())
                .type(transaction.getType())
                .build();
    }

    private DebtRsModel buildDebtResponseModel(Transaction transaction) {
        return DebtRsModel.builder()
                .transactionId(transaction.getTransactionId())
                .dateTime(transaction.getDateTime())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .type(transaction.getType())
                .accountId(
                        transaction.getType().equals(DEBT_IN.name())
                                ? transaction.getReceiverAccount().getAccountId()
                                : transaction.getSenderAccount().getAccountId())
                .build();
    }

    private Transaction transactionByIdAndUser(String transactionId, User user) {
        return transactionRepo.byIdAndUser(transactionId, user)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionId)));
    }


    private void checkBalanceToCreateTransaction(BigDecimal amount, TransactionType type, Account account) {
        if ((type.equals(DEBT_OUT) || type.equals(OUTGOING) || type.equals(TRANSFER))
                && !account.isAllowNegative()
                && account.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }
    }

    private void checkBalanceToUpdateInOut(UpdateInOutRqModel requestBody,
                                           Transaction transaction,
                                           Account account,
                                           BigDecimal oldAmount) {
        if (transaction.getType().equals(OUTGOING.name())
                && !account.isAllowNegative()
                && (account.getBalance().add(oldAmount)).compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));

        }
    }

    private void checkBalanceToUpdateTransfer(UpdateTransferRqModel requestBody,
                                              Transaction transaction,
                                              Account senderAccount,
                                              BigDecimal oldAmount) {
        if (requestBody.getSenderAccountId().equals(transaction.getSenderAccount().getAccountId())) {
            if (!senderAccount.isAllowNegative()
                    && ((senderAccount.getBalance().add(oldAmount)).compareTo(requestBody.getAmount()) < 0)) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, senderAccount.getName()));
            }
        } else if (requestBody.getSenderAccountId().equals(transaction.getReceiverAccount().getAccountId())) {
            if (!senderAccount.isAllowNegative()
                    && (senderAccount.getBalance().subtract(oldAmount)).compareTo(requestBody.getAmount()) < 0) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, senderAccount.getName()));
            }
        } else {
            if (!senderAccount.isAllowNegative()
                    && senderAccount.getBalance().compareTo(requestBody.getAmount()) < 0) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, senderAccount.getName()));
            }

        }
    }

    private void checkBalanceToUpdateDebt(UpdateDebtRqModel requestBody,
                                          Account account,
                                          Transaction transaction,
                                          BigDecimal oldAmount) {
        if (transaction.getType().equals(DEBT_OUT.name())) {
            if (requestBody.getAccountId().equals(transaction.getSenderAccount().getAccountId())
                    && !account.isAllowNegative()
                    && ((account.getBalance().add(oldAmount)).compareTo(requestBody.getAmount()) < 0)) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
            }

            if (!requestBody.getAccountId().equals(transaction.getSenderAccount().getAccountId())
                    && !account.isAllowNegative()
                    && account.getBalance().compareTo(requestBody.getAmount()) < 0) {
                throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
            }
        }
    }
}
