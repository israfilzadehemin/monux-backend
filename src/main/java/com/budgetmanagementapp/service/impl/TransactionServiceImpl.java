package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.builder.TransactionBuilder;
import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.model.transaction.*;
import com.budgetmanagementapp.repository.TransactionRepository;
import com.budgetmanagementapp.service.*;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.PaginationTool;
import com.budgetmanagementapp.utility.TransactionType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.IntStream;

import static com.budgetmanagementapp.mapper.TransactionMapper.TRANSACTION_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.Constant.*;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.*;
import static java.lang.String.format;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.*;

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

        Transaction transaction = transactionRepo.save(
                transactionBuilder.buildTransaction(requestBody, user, account, category, labels, type));
        accountService.updateBalanceByRate(requestBody.getAmount(), 1.0, accounts);

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

        Map<String, Account> accounts = new HashMap<>() {{
            put(SENDER_ACCOUNT, senderAccount);
            put(RECEIVER_ACCOUNT, receiverAccount);
        }};

        Transaction transaction = transactionRepo.save(
                transactionBuilder.buildTransaction(requestBody, user, senderAccount, receiverAccount));

        accountService.updateBalanceByRate(requestBody.getAmount(), requestBody.getRate(), accounts);

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

        Map<String, Account> accounts = type.equals(DEBT_OUT)
                ? singletonMap(SENDER_ACCOUNT, account)
                : singletonMap(RECEIVER_ACCOUNT, account);

        Transaction transaction = transactionRepo.save(
                transactionBuilder.buildTransaction(requestBody, type, account, user));
        accountService.updateBalanceByRate(requestBody.getAmount(), 1.0, accounts);

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
        accountService.updateBalanceByRate(oldAmount, 1.0, oldAccounts);
        accountService.updateBalanceByRate(requestBody.getAmount(), 1.0, newAccounts);

        InOutRsModel inOutRsModel = TRANSACTION_MAPPER_INSTANCE.buildInOutResponseModel(updatedTransaction);
        log.info(IN_OUT_TRANSACTION_UPDATED_MSG, user.getUsername(), inOutRsModel);
        return inOutRsModel;
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

        accountService.updateBalanceForTransferDelete(oldAmount, oldRate, oldAccounts);
        accountService.updateBalanceByRate(requestBody.getAmount(), requestBody.getRate(), newAccounts);

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
        accountService.updateBalanceByRate(oldAmount, 1.0, oldAccounts);
        accountService.updateBalanceByRate(requestBody.getAmount(), 1.0, newAccounts);

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

            accountService.updateBalanceForTransferDelete(tr.getAmount(), tr.getRate(), map);

            transactionRepo.deleteById(user, tr.getTransactionId());
            return TRANSACTION_MAPPER_INSTANCE.buildGenericResponseModel(tr);

        }).collect(toList());

        log.info(DELETED_TRANSACTIONS_MSG, user.getUsername(), response);
        return response;
    }

    @Override
    public AmountListRsModel getLastTransactionsByUserAndDateTimeForMonths(String username, LocalDateTime dateTime) {
        User user = userService.findByUsername(username);
        List<Transaction> transactions =
                transactionRepo.byUserAndDateTime(user, dateTime.minusMonths(12), LocalDateTime.now());

        List<Transaction> incomeTransactions = new ArrayList<>();
        List<Transaction> outgoingTransactions = new ArrayList<>();
        groupTransactions(transactions, incomeTransactions, outgoingTransactions);

        List<YearMonth> yearMonths = IntStream.rangeClosed(0, 11)
                .mapToObj(i -> YearMonth.from(LocalDateTime.now().minusMonths(i)))
                .collect(toList());

        Map<YearMonth, Double> incomeAmountsByMonths = incomeTransactions.stream()
                .collect(groupingBy(t -> YearMonth.from(t.getDateTime()),
                        TreeMap::new,
                        summingDouble(t -> t.getAmount().doubleValue())))
                .descendingMap();

        TreeMap<YearMonth, Double> incomeAmounts = new TreeMap<>();
        yearMonths.forEach(a -> incomeAmounts.put(a,
                incomeAmountsByMonths.get(a) != null ? incomeAmountsByMonths.get(a) : 0));

        Map<YearMonth, Double> outgoingAmountsByMonths = outgoingTransactions.stream()
                .collect(groupingBy(t -> YearMonth.from(t.getDateTime()),
                        TreeMap::new,
                        summingDouble(t -> t.getAmount().doubleValue())))
                .descendingMap();

        TreeMap<YearMonth, Double> outgoingAmounts = new TreeMap<>();
        yearMonths.forEach(a -> outgoingAmounts.put(a,
                outgoingAmountsByMonths.get(a) != null ? outgoingAmountsByMonths.get(a) : 0));

        AmountListRsModel response = AmountListRsModel.builder()
                .income(incomeAmounts.descendingMap())
                .outgoing(outgoingAmounts.descendingMap())
                .build();

        log.info(LAST_TRANSACTIONS_BY_MONTHS_MSG, user.getUsername(), response);
        return response;
    }

    @Override
    public AmountListRsModel getLastTransactionsByUserAndDateTimeForWeeks(String username, LocalDateTime dateTime) {
        User user = userService.findByUsername(username);
        List<Transaction> transactions =
                transactionRepo.byUserAndDateTime(user, dateTime.minusWeeks(12), LocalDateTime.now());

        List<Transaction> incomeTransactions = new ArrayList<>();
        List<Transaction> outgoingTransactions = new ArrayList<>();
        groupTransactions(transactions, incomeTransactions, outgoingTransactions);

        List<LocalDate> monthDays = IntStream.rangeClosed(0, 11)
                .mapToObj(i -> LocalDate.from(LocalDateTime.now().minusWeeks(i)))
                .collect(toList());

        Map<LocalDate, Double> incomeAmountsByWeeks = incomeTransactions.stream()
                .collect(groupingBy(t -> LocalDate.from(t.getDateTime()
                                .with(TemporalAdjusters.previousOrSame(DayOfWeek.from(LocalDateTime.now())))),
                        TreeMap::new,
                        summingDouble(t -> t.getAmount().doubleValue())))
                .descendingMap();

        TreeMap<LocalDate, Double> incomeAmounts = new TreeMap<>();
        monthDays.forEach(a -> incomeAmounts.put(a,
                incomeAmountsByWeeks.get(a) != null ? incomeAmountsByWeeks.get(a) : 0));

        Map<LocalDate, Double> outgoingAmountsByWeeks = outgoingTransactions.stream()
                .collect(groupingBy(t -> LocalDate.from(t.getDateTime()
                                .with(TemporalAdjusters.previousOrSame(DayOfWeek.from(LocalDateTime.now())))),
                        TreeMap::new,
                        summingDouble(t -> t.getAmount().doubleValue())))
                .descendingMap();

        TreeMap<LocalDate, Double> outgoingAmounts = new TreeMap<>();
        monthDays.forEach(a -> outgoingAmounts.put(a,
                outgoingAmountsByWeeks.get(a) != null ? outgoingAmountsByWeeks.get(a) : 0));

        AmountListRsModel response = AmountListRsModel.builder()
                .income(incomeAmounts.descendingMap())
                .outgoing(outgoingAmounts.descendingMap())
                .build();

        log.info(LAST_TRANSACTIONS_BY_WEEKS_MSG, user.getUsername(), response);
        return response;
    }

    @Override
    public CategoryAmountListRsModel transactionsBetweenTimeByCategory(String username, String from, String to) {
        User user = userService.findByUsername(username);
        List<Transaction> transactions = transactionRepo.byUserAndDateTime(user,
                CustomFormatter.stringToLocalDateTime(from), CustomFormatter.stringToLocalDateTime(to));

        List<Transaction> incomeTransactions = new ArrayList<>();
        List<Transaction> outgoingTransactions = new ArrayList<>();
        groupTransactions(transactions, incomeTransactions, outgoingTransactions);

        Map<String, BigDecimal> income = incomeTransactions.stream()
                .collect(groupingBy(t -> t.getCategory().getName(),
                        reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        Map<String, BigDecimal> outgoing = outgoingTransactions.stream()
                .collect(groupingBy(t -> t.getCategory().getName(),
                        reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));

        CategoryAmountListRsModel response = CategoryAmountListRsModel.builder()
                .dateTimeFrom(from).dateTimeTo(to).income(income).outgoing(outgoing)
                .build();

        log.info(TRANSACTIONS_BETWEEN_TIME_MSG, user.getUsername(), response);
        return response;
    }

    private void groupTransactions(List<Transaction> transactions,
                                   List<Transaction> incomeTransactions,
                                   List<Transaction> outgoingTransactions) {
        transactions.forEach(transaction -> {
            if (getTransactionType(transaction.getType()).equals(INCOME)) {
                incomeTransactions.add(transaction);
            }
            if (getTransactionType(transaction.getType()).equals(OUTGOING)) {
                outgoingTransactions.add(transaction);
            }
        });
    }

    private Transaction updateTransactionValues(InOutRqModel requestBody, Transaction transaction,
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

    private void checkBalanceToUpdateInOut(InOutRqModel requestBody,
                                           Transaction transaction,
                                           Account account,
                                           BigDecimal oldAmount) {
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
}
