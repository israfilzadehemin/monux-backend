package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.CASH_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.RECEIVER_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.SENDER_ACCOUNT;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.AccountNotFoundException;
import com.budgetmanagementapp.exception.AccountTypeNotFoundException;
import com.budgetmanagementapp.exception.CurrencyNotFoundException;
import com.budgetmanagementapp.exception.DuplicateAccountException;
import com.budgetmanagementapp.exception.InitialAccountExistingException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.model.*;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.AccountTypeRepository;
import com.budgetmanagementapp.repository.CurrencyRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepo;
    private final UserService userService;
    private final AccountTypeRepository accountTypeRepo;
    private final CurrencyRepository currencyRepo;

    @Override
    public AccountRsModel createAccount(AccountRqModel requestBody, boolean isInitialAccount) {
        CustomValidator.validateAccountModel(requestBody, isInitialAccount);

        User user = userService.findByUsername(requestBody.getUsername());
        checkInitialAccountExistence(isInitialAccount, user);
        checkDuplicateAccount(requestBody.getAccountName(), user);

        Account account = buildAccount(
                requestBody,
                user,
                getAccountType(requestBody.getAccountTypeName(), isInitialAccount),
                getCurrency(requestBody.getCurrency()),
                isInitialAccount
        );

        log.info(format(ACCOUNT_CREATED_MSG, user.getUsername(), buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel updateAccount(UpdateAccountModel requestBody, String username) {
        Account account = byIdAndUser(requestBody.getAccountId(), userService.findByUsername(username));
        updateAccountValues(requestBody, account);

        log.info(format(ACCOUNT_UPDATED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel toggleAllowNegative(String accountId, String username) {
        Account account = byIdAndUser(accountId, userService.findByUsername(username));
        checkNegativeBalance(account);
        toggleAllowNegativeValue(account);

        log.info(format(ALLOW_NEGATIVE_TOGGLED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel toggleShowInSum(String accountId, String username) {
        Account account = byIdAndUser(accountId, userService.findByUsername(username));
        toggleShowInSumValue(account);

        log.info(format(SHOW_IN_SUM_TOGGLED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public List<AccountRsModel> getAllAccountsByUser(String username) {
        User user = userService.findByUsername(username);

        List<AccountRsModel> accounts =
                accountRepo.allByUser(user)
                        .stream()
                        .map(this::buildAccountResponseModel)
                        .collect(Collectors.toList());

        log.info(format(ALL_ACCOUNTS_MSG, user.getUsername(), accounts));
        return accounts;
    }

    @Override
    public AccountRsModel updateBalance(UpdateBalanceModel requestBody, String username) {
        Account account = byIdAndUser(requestBody.getAccountId(), userService.findByUsername(username));
        checkNegativeBalance(requestBody, account);
        updateBalanceValue(requestBody, account);

        log.info(format(BALANCE_UPDATED_MSG, username, account.getName(), buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public void updateBalance(BigDecimal amount, Map<String, Account> accounts) {

        if (!Objects.isNull(accounts.get(SENDER_ACCOUNT))) {
            accounts.get(SENDER_ACCOUNT).setBalance(accounts.get(SENDER_ACCOUNT).getBalance().subtract(amount));
            accountRepo.save(accounts.get(SENDER_ACCOUNT));
        }

        if (!Objects.isNull(accounts.get(RECEIVER_ACCOUNT))) {
            accounts.get(RECEIVER_ACCOUNT).setBalance(accounts.get(RECEIVER_ACCOUNT).getBalance().add(amount));
            accountRepo.save(accounts.get(RECEIVER_ACCOUNT));
        }
    }

    @Override
    public List<AccountTypeRsModel> getAllAccountTypes() {
        List<AccountTypeRsModel> response = accountTypeRepo.findAll().stream()
                .map(this::buildAccountTypeResponseModel)
                .collect(Collectors.toList());

        log.info(String.format(ALL_ACCOUNT_TYPES_MSG, response));
        return response;
    }

    @Override
    public List<CurrencyRsModel> getAllCurrencies() {
        List<CurrencyRsModel> response = currencyRepo.findAll().stream()
                .map(this::buildCurrencyResponseModel)
                .collect(Collectors.toList());

        log.info(String.format(ALL_CURRENCIES_MSG, response));
        return response;
    }

    @Override
    public Account byIdAndUser(String accountId, User user) {
        return accountRepo
                .byIdAndUser(accountId, user)
                .orElseThrow(() -> new AccountNotFoundException(
                        format(UNAUTHORIZED_ACCOUNT_MSG, user.getUsername(), accountId)));
    }

    private Account buildAccount(AccountRqModel requestBody, User user, AccountType accountType,
                                 Currency currency, boolean isInitialAccount) {
        return accountRepo.save(Account.builder()
                .accountId(UUID.randomUUID().toString())
                .icon(requestBody.getIcon())
                .name(requestBody.getAccountName())
                .accountType(accountType)
                .currency(currency)
                .allowNegative(isInitialAccount || requestBody.getAllowNegative())
                .balance(requestBody.getBalance())
                .enabled(true)
                .showInSum(isInitialAccount || requestBody.getShowInSum())
                .user(user)
                .build());
    }

    private AccountRsModel buildAccountResponseModel(Account account) {
        return AccountRsModel.builder()
                .icon(account.getIcon())
                .accountId(account.getAccountId())
                .accountName(account.getName())
                .accountTypeName(account.getAccountType().getAccountTypeName())
                .currency(account.getCurrency().getName())
                .allowNegative(account.isAllowNegative())
                .balance(account.getBalance())
                .showInSum(account.isShowInSum())
                .build();
    }

    private AccountTypeRsModel buildAccountTypeResponseModel(AccountType accountType) {
        return AccountTypeRsModel.builder()
                .accountTypeId(accountType.getAccountTypeId())
                .accountTypeName(accountType.getAccountTypeName())
                .build();
    }

    private CurrencyRsModel buildCurrencyResponseModel(Currency currency) {
        return CurrencyRsModel.builder()
                .currencyId(currency.getCurrencyId())
                .currencyName(currency.getName())
                .build();
    }

    private Currency getCurrency(String currency) {
        return currencyRepo
                .byName(currency)
                .orElseThrow(() -> new CurrencyNotFoundException(format(CURRENCY_NOT_FOUND_MSG, currency)));
    }

    private AccountType getAccountType(String accountTypeName, boolean isInitialAccount) {
        return accountTypeRepo
                .byName(isInitialAccount ? CASH_ACCOUNT : accountTypeName)
                .orElseThrow(() ->
                        new AccountTypeNotFoundException(format(ACCOUNT_TYPE_NOT_FOUND_MSG, accountTypeName)));
    }

    private void updateAccountValues(UpdateAccountModel requestBody, Account account) {
        account.setIcon(requestBody.getIcon());
        account.setName(requestBody.getNewAccountName());
        account.setAccountType(getAccountType(requestBody.getAccountTypeName(), false));
        accountRepo.save(account);
    }

    private void toggleAllowNegativeValue(Account account) {
        account.setAllowNegative(!account.isAllowNegative());
        accountRepo.save(account);
    }

    private void toggleShowInSumValue(Account account) {
        account.setShowInSum(!account.isShowInSum());
        accountRepo.save(account);
    }

    private void updateBalanceValue(UpdateBalanceModel requestBody, Account account) {
        account.setBalance(requestBody.getAmount());
        accountRepo.save(account);
    }

    private void checkDuplicateAccount(String accountName, User user) {
        if (accountRepo.byNameAndUser(accountName, user).isPresent()) {
            throw new DuplicateAccountException(format(DUPLICATE_ACCOUNT_NAME_MSG, user.getUsername(), accountName));
        }
    }

    private void checkInitialAccountExistence(boolean isInitialAccount, User user) {
        if (isInitialAccount && !user.getAccounts().isEmpty()) {
            throw new InitialAccountExistingException(format(INITIAL_ACCOUNT_EXISTING_MSG, user.getUsername()));
        }
    }

    private void checkNegativeBalance(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughBalanceException(format(INSUFFICIENT_BALANCE_MSG, account.getAccountId()));
        }
    }

    private void checkNegativeBalance(UpdateBalanceModel requestBody, Account account) {
        if (requestBody.getAmount().compareTo(BigDecimal.ZERO) < 0 && !account.isAllowNegative()) {
            throw new NotEnoughBalanceException(format(NEGATIVE_BALANCE_NOT_ALLOWED, account.getAccountId()));
        }
    }
}

