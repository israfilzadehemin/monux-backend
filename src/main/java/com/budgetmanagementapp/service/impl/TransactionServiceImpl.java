package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.ACCOUNT_ALL;
import static com.budgetmanagementapp.utility.Constant.RECEIVER_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.SENDER_ACCOUNT;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_TRANSACTIONS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DELETED_TRANSACTIONS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INSUFFICIENT_BALANCE_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.LAST_TRANSACTIONS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSACTION_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TO_SELF_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_TRANSACTION_MSG;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_OUT;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.OUTGOING;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static com.budgetmanagementapp.utility.TransactionType.valueOf;
import static java.lang.String.format;
import static java.util.Collections.singletonMap;

import com.budgetmanagementapp.builder.TransactionBuilder;
import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.TransactionNotFoundException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.mapper.TransactionMapper;
import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
import com.budgetmanagementapp.repository.TransactionRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.CategoryService;
import com.budgetmanagementapp.service.LabelService;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.PaginationTool;
import com.budgetmanagementapp.utility.TransactionType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    private final TransactionBuilder transactionBuilder;

    @Override
    @Transactional
    public TransactionRsModel createTransaction(InOutRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category = categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), type.name(), user);

        checkBalanceToCreateTransaction(requestBody.getAmount(), type, account);

        Map<String, Account> accounts = type.equals(OUTGOING)
                ? singletonMap(SENDER_ACCOUNT, account)
                : singletonMap(RECEIVER_ACCOUNT, account);

        Transaction transaction = transactionBuilder.buildTransaction(requestBody, user, account, category, labels, type);
        accountService.updateBalance(requestBody.getAmount(), accounts);

        InOutRsModel response = TransactionMapper.INSTANCE.buildInOutResponseModel(transaction);
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

        Transaction transaction = transactionBuilder.buildTransaction(requestBody, user, senderAccount, receiverAccount);
        accountService.updateBalance(requestBody.getAmount(), accounts);

        TransferRsModel response = TransactionMapper.INSTANCE.buildTransferResponseModel(transaction);
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
                ? singletonMap(SENDER_ACCOUNT, account)
                : singletonMap(RECEIVER_ACCOUNT, account);

        Transaction transaction = transactionBuilder.buildTransaction(requestBody, type, account, user);
        accountService.updateBalance(requestBody.getAmount(), accounts);

        DebtRsModel response = TransactionMapper.INSTANCE.buildDebtResponseModel(transaction);
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
        List<Label> labels =
                labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), transaction.getType(), user);
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

        InOutRsModel response = TransactionMapper.INSTANCE.buildInOutResponseModel(updatedTransaction);
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

        TransferRsModel response = TransactionMapper.INSTANCE.buildTransferResponseModel(updatedTransaction);
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

        DebtRsModel response = TransactionMapper.INSTANCE.buildDebtResponseModel(updatedTransaction);
        log.info(format(DEBT_TRANSACTION_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public List<TransactionRsModel> getAllTransactionsByUserAndAccount(String username, String accountId) {
        User user = userService.findByUsername(username);
        List<Transaction> transactions = new ArrayList<>();

        if (accountId.equals(ACCOUNT_ALL)) {
            transactions.addAll(transactionRepo.allByUser(user));
        } else {
            Account account = accountService.byIdAndUser(accountId, user);
            transactions.addAll(transactionRepo.allByUserAndReceiverAccount(user, account));
            transactions.addAll(transactionRepo.allByUserAndSenderAccount(user, account));
        }
        transactions.sort(Comparator.comparing(Transaction::getId).reversed());

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException(
                    format(TRANSACTION_NOT_FOUND_MSG, user.getUsername()));
        } else {
            List<TransactionRsModel> response =
                    transactions.stream()
                            .map(transactionBuilder::buildGenericResponseModel)
                            .collect(Collectors.toList());

            log.info(String.format(ALL_TRANSACTIONS_MSG, username, response));
            return response;
        }
    }

    @Override
    public List<TransactionRsModel> getLastTransactionsByUserAndAccount(String username, String accountId,
                                                                        int pageCount, int size, String sortField,
                                                                        String sortDir) {
        Pageable pageable = paginationTool.service(pageCount, size, sortField, sortDir);
        User user = userService.findByUsername(username);
        List<Transaction> transactions = new ArrayList<>();

        if (accountId.equals(ACCOUNT_ALL)) {
            transactions.addAll(transactionRepo.lastByUser(user, pageable).toList());
        } else {
            Account account = accountService.byIdAndUser(accountId, user);
            transactions.addAll(transactionRepo.lastByUserAndSenderAccount(user, account, pageable).toList());
            transactions.addAll(transactionRepo.lastByUserAndReceiverAccount(user, account, pageable).toList());
        }

        transactions.sort(Comparator.comparing(Transaction::getDateTime).reversed());

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException(
                    format(TRANSACTION_NOT_FOUND_MSG, user.getUsername()));
        } else {
            List<TransactionRsModel> response =
                    transactions.stream()
                            .map(transactionBuilder::buildGenericResponseModel)
                            .collect(Collectors.toList());

            log.info(format(LAST_TRANSACTIONS_MSG, username, response));
            return response;
        }
    }

    @Transactional
    @Override
    public List<TransactionRsModel> deleteTransactionById(String username, List<String> transactionIds) {
        User user = userService.findByUsername(username);


        List<TransactionRsModel> response = transactionsByUserAndIdList(user, transactionIds).stream().map(tr -> {
            Map<String, Account> map = new HashMap<>();

            switch (getTransactionType(tr.getType())) {
                case INCOME, DEBT_IN -> map.put(SENDER_ACCOUNT, tr.getReceiverAccount());
                case OUTGOING, DEBT_OUT -> map.put(RECEIVER_ACCOUNT, tr.getSenderAccount());
                case TRANSFER -> {
                    map.put(SENDER_ACCOUNT, tr.getReceiverAccount());
                    map.put(RECEIVER_ACCOUNT, tr.getSenderAccount());
                }
            }

            accountService.updateBalance(tr.getAmount(), map);
            transactionRepo.deleteById(user, tr.getTransactionId());
            return transactionBuilder.buildGenericResponseModel(tr);

        }).collect(Collectors.toList());

        log.info(format(DELETED_TRANSACTIONS_MSG, user.getUsername(), response));
        return response;
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

    private Transaction transactionByIdAndUser(String transactionId, User user) {
        return transactionRepo.byIdAndUser(transactionId, user)
                .orElseThrow(() ->
                        new TransactionNotFoundException(
                                format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionId)));
    }

    private List<Transaction> transactionsByUserAndIdList(User user, List<String> transactionIds) {
        List<Transaction> transactions = transactionRepo.allByUserAndIdList(user, transactionIds);
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException(
                    format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionIds));
        }
        return transactions;
    }

    private TransactionType getTransactionType(String type) {
        CustomValidator.validateTransactionType(type);
        return TransactionType.valueOf(type);
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
