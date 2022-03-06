package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.mapper.TransactionMapper.TRANSACTION_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.Constant.ACCOUNT_ALL;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_OUT;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.OUTGOING;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static com.budgetmanagementapp.utility.TransactionType.valueOf;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.budgetmanagementapp.builder.TransactionBuilder;
import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.model.UpdateBalancesModel;
import com.budgetmanagementapp.model.transaction.AmountListRsModel;
import com.budgetmanagementapp.model.transaction.CategoryAmountListRsModel;
import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transaction.TransferRqModel;
import com.budgetmanagementapp.model.transaction.TransferRsModel;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;
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
    public TransactionRsModel
    createTransaction(InOutRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category = categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), type.name(), user);

        checkBalanceToCreateTransaction(requestBody.getAmount(), type, account);

        UpdateBalancesModel accounts = UpdateBalancesModel.builder()
                .to(type.equals(INCOME) ? account : null)
                .from(type.equals(OUTGOING) ? account : null)
                .build();

        Transaction transaction = transactionRepo.save(
                transactionBuilder.buildTransaction(requestBody, user, account, category, labels, type));
        accountService.updateBalance(requestBody.getAmount(), 1.0, accounts, false);

        InOutRsModel inOutRsModel = TRANSACTION_MAPPER_INSTANCE.buildInOutResponseModel(transaction);
        log.info(IN_OUT_TRANSACTION_CREATED_MSG, user.getUsername(), inOutRsModel);
        return inOutRsModel;
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

        UpdateBalancesModel accounts = UpdateBalancesModel.builder().from(senderAccount).to(receiverAccount).build();

        Transaction transaction = transactionRepo.save(
                transactionBuilder.buildTransaction(requestBody, user, senderAccount, receiverAccount));

        accountService.updateBalance(requestBody.getAmount(), requestBody.getRate(), accounts, false);

        TransferRsModel transferRsModel = TRANSACTION_MAPPER_INSTANCE.buildTransferResponseModel(transaction);
        log.info(TRANSFER_TRANSACTION_CREATED_MSG, user.getUsername(), transferRsModel);
        return transferRsModel;
    }

    @Override
    @Transactional
    public DebtRsModel createTransaction(DebtRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);

        checkBalanceToCreateTransaction(requestBody.getAmount(), type, account);

        UpdateBalancesModel accounts = UpdateBalancesModel.builder()
                .from(type.equals(DEBT_OUT) ? account : null)
                .to(type.equals(DEBT_IN) ? account : null)
                .build();

        Transaction transaction = transactionRepo.save(
                transactionBuilder.buildTransaction(requestBody, type, account, user));
        accountService.updateBalance(requestBody.getAmount(), 1.0, accounts, false);

        DebtRsModel response = TRANSACTION_MAPPER_INSTANCE.buildDebtResponseModel(transaction);
        log.info(DEBT_TRANSACTION_CREATED_MSG, user.getUsername(), response);
        return response;
    }

    @Override
    @Transactional
    public InOutRsModel updateTransaction(InOutRqModel requestBody, String transactionId, String username) {
        User user = userService.findByUsername(username);
        Transaction transaction = transactionByIdAndUser(transactionId, user);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category =
                categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), valueOf(transaction.getType()), user);
        List<Label> labels =
                labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), transaction.getType(), user);
        BigDecimal oldAmount = transaction.getAmount();

        checkBalanceToUpdateInOut(requestBody, transaction, account, oldAmount);

        UpdateBalancesModel currentAccounts =
                UpdateBalancesModel.builder()
                        .from(transaction.getSenderAccount())
                        .to(transaction.getReceiverAccount())
                        .build();
        accountService.updateBalance(oldAmount, 1.0, currentAccounts, true);

        Map<TransactionType, Account> accountMap = getAccountByTransactionType(transaction, account);
        UpdateBalancesModel updatedAccounts = UpdateBalancesModel.builder()
                .from(accountMap.get(OUTGOING)).to(accountMap.get(INCOME))
                .build();

        Transaction updatedTransaction = updateTransactionValues(requestBody, transaction, account, category, labels);
        accountService.updateBalance(requestBody.getAmount(), 1.0, updatedAccounts, false);

        InOutRsModel inOutRsModel = TRANSACTION_MAPPER_INSTANCE.buildInOutResponseModel(updatedTransaction);
        log.info(IN_OUT_TRANSACTION_UPDATED_MSG, user.getUsername(), inOutRsModel);
        return inOutRsModel;
    }

    private Map<TransactionType, Account> getAccountByTransactionType(Transaction transaction, Account account) {
        return new HashMap<>() {{
            put(TransactionType.valueOf(transaction.getType()), account);
        }};
    }

    @Override
    @Transactional
    public TransferRsModel updateTransaction(TransferRqModel requestBody, String transactionId, String username) {
        User user = userService.findByUsername(username);
        Transaction transaction = transactionByIdAndUser(transactionId, user);
        Account senderAccount = accountService.byIdAndUser(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountService.byIdAndUser(requestBody.getReceiverAccountId(), user);
        BigDecimal oldAmount = transaction.getAmount();
        Double oldRate = transaction.getRate();

        checkBalanceToUpdateTransfer(requestBody, transaction, senderAccount, oldAmount);

        UpdateBalancesModel currentAccounts = UpdateBalancesModel.builder()
                .from(transaction.getSenderAccount()).to(transaction.getReceiverAccount()).build();
        UpdateBalancesModel updatedAccounts =
                UpdateBalancesModel.builder().from(senderAccount).to(receiverAccount).build();

        Transaction updatedTransaction =
                updateTransactionValues(requestBody, transaction, senderAccount, receiverAccount);

        accountService.updateBalance(oldAmount, oldRate, currentAccounts, true);
        accountService.updateBalance(requestBody.getAmount(), requestBody.getRate(), updatedAccounts, false);

        TransferRsModel transferRsModel = TRANSACTION_MAPPER_INSTANCE.buildTransferResponseModel(updatedTransaction);
        log.info(TRANSFER_TRANSACTION_UPDATED_MSG, user.getUsername(), transferRsModel);
        return transferRsModel;
    }

    @Override
    @Transactional
    public DebtRsModel updateTransaction(DebtRqModel requestBody, String transactionId, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Transaction transaction = transactionByIdAndUser(transactionId, user);
        BigDecimal oldAmount = transaction.getAmount();

        checkBalanceToUpdateDebt(requestBody, account, transaction, oldAmount);


        UpdateBalancesModel currentAccounts =
                UpdateBalancesModel.builder()
                        .from(transaction.getSenderAccount())
                        .to(transaction.getReceiverAccount())
                        .build();
        accountService.updateBalance(oldAmount, 1.0, currentAccounts, true);

        Map<TransactionType, Account> accountMap = getAccountByTransactionType(transaction, account);

        UpdateBalancesModel updatedAccounts = UpdateBalancesModel.builder()
                .from(accountMap.get(DEBT_OUT)).to(accountMap.get(DEBT_IN))
                .build();

        Transaction updatedTransaction = updateTransactionValues(requestBody, account, transaction);
        accountService.updateBalance(requestBody.getAmount(), 1.0, updatedAccounts, false);

        DebtRsModel response = TRANSACTION_MAPPER_INSTANCE.buildDebtResponseModel(updatedTransaction);
        log.info(DEBT_TRANSACTION_UPDATED_MSG, user.getUsername(), response);
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
            throw new DataNotFoundException(
                    format(TRANSACTION_NOT_FOUND_MSG, user.getUsername()), 3002);
        } else {
            List<TransactionRsModel> response =
                    transactions.stream()
                            .map(TRANSACTION_MAPPER_INSTANCE::buildGenericResponseModel)
                            .collect(toList());

            log.info(ALL_TRANSACTIONS_MSG, username, response);
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
            throw new DataNotFoundException(
                    format(TRANSACTION_NOT_FOUND_MSG, user.getUsername()), 3002);
        } else {
            List<TransactionRsModel> response =
                    transactions.stream()
                            .map(TRANSACTION_MAPPER_INSTANCE::buildGenericResponseModel)
                            .collect(toList());

            log.info(LAST_TRANSACTIONS_MSG, username, response);
            return response;
        }
    }

    @Transactional
    @Override
    public List<TransactionRsModel> deleteTransactionById(String username, List<String> transactionIds) {
        User user = userService.findByUsername(username);

        List<TransactionRsModel> response = transactionsByUserAndIdList(user, transactionIds)
                .stream()
                .map(t -> {
                    UpdateBalancesModel accounts = UpdateBalancesModel.builder().build();

                    switch (getTransactionType(t.getType())) {
                        case INCOME, DEBT_IN -> accounts.setTo(t.getReceiverAccount());
                        case OUTGOING, DEBT_OUT -> accounts.setFrom(t.getSenderAccount());
                        case TRANSFER -> {
                            accounts.setFrom(t.getSenderAccount());
                            accounts.setTo(t.getReceiverAccount());
                        }
                    }

                    accountService.updateBalance(t.getAmount(), t.getRate(), accounts, true);
                    transactionRepo.deleteById(user, t.getTransactionId());
                    return TRANSACTION_MAPPER_INSTANCE.buildGenericResponseModel(t);
                })
                .collect(toList());

        log.info(DELETED_TRANSACTIONS_MSG, user.getUsername(), response);
        return response;
    }

    @Override
    public AmountListRsModel getTransactionReportInMonths(String username, LocalDateTime dateTime) {
        User user = userService.findByUsername(username);

        var transactionGroups =
                groupTransactions(transactionRepo.byUserAndDateTime(user, dateTime.minusMonths(12), now()));

        List<YearMonth> neededDates = getNeededDatesForMonths();

        TreeMap<YearMonth, Double> incomeAmounts = neededDates
                .stream()
                .collect(toMap(
                        a -> a,
                        a -> defaultIfNull(getTransactionSumsByMonths(transactionGroups, INCOME).get(a), 0d),
                        (a1, b) -> b, TreeMap::new));

        TreeMap<YearMonth, Double> outgoingAmounts = neededDates
                .stream()
                .collect(toMap(a -> a,
                        a -> defaultIfNull(getTransactionSumsByMonths(transactionGroups, OUTGOING).get(a), 0d),
                        (a1, b) -> b, TreeMap::new));

        AmountListRsModel response =
                AmountListRsModel.builder().income(incomeAmounts).outgoing(outgoingAmounts).build();

        log.info(LAST_TRANSACTIONS_BY_MONTHS_MSG, user.getUsername(), response);
        return response;
    }


    @Override
    public AmountListRsModel getTransactionReportInWeeks(String username, LocalDateTime dateTime) {
        User user = userService.findByUsername(username);

        var transactionGroups =
                groupTransactions(transactionRepo.byUserAndDateTime(user, dateTime.minusWeeks(12), now()));

        List<LocalDate> neededDates = getNeededDatesForWeeks();

        TreeMap<LocalDate, Double> incomeAmounts = neededDates
                .stream()
                .collect(toMap(
                        a -> a,
                        a -> defaultIfNull(getTransactionSumsByWeeks(transactionGroups, INCOME).get(a), 0d),
                        (a1, b) -> b, TreeMap::new));

        TreeMap<LocalDate, Double> outgoingAmounts = neededDates
                .stream()
                .collect(toMap(
                        a -> a,
                        a -> defaultIfNull(getTransactionSumsByWeeks(transactionGroups, OUTGOING).get(a), 0d),
                        (a1, b) -> b, TreeMap::new));

        AmountListRsModel response =
                AmountListRsModel.builder().income(incomeAmounts).outgoing(outgoingAmounts).build();

        log.info(LAST_TRANSACTIONS_BY_WEEKS_MSG, user.getUsername(), response);
        return response;
    }

    private TreeMap<LocalDate, Double> getTransactionSumsByWeeks(Map<String, List<Transaction>> transactionGroups,
                                                                 TransactionType income) {
        return transactionGroups.get(income.name()).stream()
                .collect(groupingBy(t -> LocalDate.from(t.getDateTime()),
                        TreeMap::new,
                        summingDouble(t -> t.getAmount().doubleValue())));
    }

    private List<LocalDate> getNeededDatesForWeeks() {
        return IntStream.rangeClosed(0, 11)
                .mapToObj(i -> LocalDate.from(now().minusWeeks(i)))
                .collect(toList());
    }

    @Override
    public CategoryAmountListRsModel getTransactionsInCategoriesByTime(String username, String from, String to) {
        var user = userService.findByUsername(username);
        var transactions = transactionRepo.byUserAndDateTime(user,
                CustomFormatter.stringToLocalDateTime(from), CustomFormatter.stringToLocalDateTime(to));

        var transactionGroups = groupTransactions(transactions);

        var income = transactionGroups.get(INCOME.name())
                .stream()
                .collect(
                        groupingBy(t -> t.getCategory().getName(),
                                reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        var outgoing = transactionGroups.get(OUTGOING.name())
                .stream()
                .collect(
                        groupingBy(t -> t.getCategory().getName(),
                                reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        var response = CategoryAmountListRsModel.builder()
                .dateTimeFrom(from).dateTimeTo(to).income(income).outgoing(outgoing)
                .build();

        log.info(TRANSACTIONS_BETWEEN_TIME_MSG, user.getUsername(), response);
        return response;
    }

    private Map<String, List<Transaction>> groupTransactions(List<Transaction> all) {

        return all.stream()
                .collect(groupingBy(Transaction::getType));

    }

    private Transaction updateTransactionValues(InOutRqModel requestBody, Transaction transaction,
                                                Account account, Category category,
                                                List<Label> labels) {
        Map<TransactionType, Account> accountMap = getAccountByTransactionType(transaction, account);

        transaction.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        transaction.setCategory(category);
        transaction.setLabels(labels);
        transaction.setReceiverAccount(accountMap.get(INCOME));
        transaction.setSenderAccount(accountMap.get(OUTGOING));
        return transactionRepo.save(transaction);
    }

    private Transaction updateTransactionValues(TransferRqModel requestBody,
                                                Transaction transaction,
                                                Account senderAccount,
                                                Account receiverAccount) {
        transaction.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        transaction.setAmount(requestBody.getAmount());
        transaction.setDescription(requestBody.getDescription());
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        transaction.setRate(requestBody.getRate());
        return transactionRepo.save(transaction);
    }

    private Transaction updateTransactionValues(DebtRqModel requestBody,
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
        Transaction transaction = transactionRepo.byIdAndUser(transactionId, user)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionId), 3002));

        log.info(TRANSACTION_BY_ID_USER, transactionId, user, transaction);
        return transaction;
    }

    private List<Transaction> transactionsByUserAndIdList(User user, List<String> transactionIds) {
        List<Transaction> transactions = transactionRepo.allByUserAndIdList(user, transactionIds);
        if (transactions.isEmpty()) {
            throw new DataNotFoundException(
                    format(UNAUTHORIZED_TRANSACTION_MSG, user.getUsername(), transactionIds), 3002);
        }

        log.info(TRANSACTIONS_USER_IDS, user, transactionIds, transactions);
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

    private void checkBalanceToUpdateInOut(InOutRqModel requestBody, Transaction transaction,
                                           Account account, BigDecimal oldAmount) {
        if (transaction.getType().equals(OUTGOING.name())
                && !account.isAllowNegative()
                && (account.getBalance().add(oldAmount)).compareTo(requestBody.getAmount()) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getName()));
        }
    }

    private void checkBalanceToUpdateTransfer(TransferRqModel requestBody,
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

    private void checkBalanceToUpdateDebt(DebtRqModel requestBody,
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


    private TreeMap<YearMonth, Double> getTransactionSumsByMonths(Map<String, List<Transaction>> transactionGroups,
                                                                  TransactionType type) {
        return transactionGroups.get(type.name())
                .stream()
                .collect(groupingBy(t -> YearMonth.from(t.getDateTime()),
                        TreeMap::new,
                        summingDouble(t -> t.getAmount().doubleValue())));
    }

    private List<YearMonth> getNeededDatesForMonths() {
        return IntStream.rangeClosed(0, 11)
                .mapToObj(i -> YearMonth.from(now().minusMonths(i)))
                .collect(toList());
    }
}
