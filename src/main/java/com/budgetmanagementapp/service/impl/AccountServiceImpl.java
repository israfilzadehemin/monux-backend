package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.mapper.AccountMapper.ACCOUNT_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.Constant.CASH_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.RECEIVER_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.SENDER_ACCOUNT;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;
import static java.util.Objects.isNull;

import com.budgetmanagementapp.builder.AccountBuilder;
import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.exception.DuplicateException;
import com.budgetmanagementapp.exception.InitialAccountExistingException;
import com.budgetmanagementapp.exception.NotEnoughBalanceException;
import com.budgetmanagementapp.model.UpdateBalancesModel;
import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.model.account.AccountRsModel;
import com.budgetmanagementapp.model.account.AccountTypeRsModel;
import com.budgetmanagementapp.model.account.CurrencyRsModel;
import com.budgetmanagementapp.model.account.UpdateAccountModel;
import com.budgetmanagementapp.model.account.UpdateBalanceRqModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.AccountTypeRepository;
import com.budgetmanagementapp.repository.CurrencyRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
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
    private final AccountBuilder accountBuilder;

    @Override
    public AccountRsModel createAccount(AccountRqModel requestBody, boolean isInitialAccount) {
        CustomValidator.validateAccountModel(requestBody, isInitialAccount);

        User user = userService.findByUsername(requestBody.getUsername());
        checkInitialAccountExistence(isInitialAccount, user);
        checkDuplicateAccount(requestBody.getAccountName(), user);

        Account account = accountRepo.save(
                accountBuilder.buildAccount(requestBody,
                        user,
                        getAccountType(requestBody.getAccountTypeName(), isInitialAccount),
                        getCurrency(requestBody.getCurrency()),
                        isInitialAccount));

        log.info(ACCOUNT_CREATED_MSG, user.getUsername(), ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account));
        return ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel updateAccount(UpdateAccountModel requestBody, String accountId, String username) {
        User user = userService.findByUsername(username);
        Account account = byIdAndUser(accountId, user);
        checkUpdateDuplicateAccount(requestBody.getNewAccountName(), user, account.getAccountId());
        updateAccountValues(requestBody, account);

        log.info(ACCOUNT_UPDATED_MSG, username, ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account));
        return ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel toggleAllowNegative(String accountId, String username) {
        Account account = byIdAndUser(accountId, userService.findByUsername(username));
        checkNegativeBalance(account);
        toggleAllowNegativeValue(account);

        log.info(ALLOW_NEGATIVE_TOGGLED_MSG, username, ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account));
        return ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel toggleShowInSum(String accountId, String username) {
        Account account = byIdAndUser(accountId, userService.findByUsername(username));
        toggleShowInSumValue(account);

        log.info(SHOW_IN_SUM_TOGGLED_MSG, username, ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account));
        return ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account);
    }

    @Override
    public List<AccountRsModel> getAllAccountsByUser(String username) {
        User user = userService.findByUsername(username);

        List<AccountRsModel> accounts =
                accountRepo.allByUser(user)
                        .stream()
                        .map(ACCOUNT_MAPPER_INSTANCE::buildAccountResponseModel)
                        .collect(Collectors.toList());

        log.info(ALL_ACCOUNTS_MSG, user.getUsername(), accounts);
        return accounts;
    }

    @Override
    public AccountRsModel updateBalance(UpdateBalanceRqModel requestBody, String accountId, String username) {
        Account account = byIdAndUser(accountId, userService.findByUsername(username));
        checkNegativeBalance(requestBody, account);
        updateBalanceValue(requestBody, account);

        log.info(
                BALANCE_UPDATED_MSG,
                username, account.getName(), ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account));
        return ACCOUNT_MAPPER_INSTANCE.buildAccountResponseModel(account);
    }

    @Transactional
    @Override
    public void updateBalance(BigDecimal amount, Double rate, UpdateBalancesModel accounts, boolean isDelete) {

        if (!isNull(accounts.getFrom())) {
            accounts.getFrom().setBalance(accounts.getFrom().getBalance().subtract(amount));
            accountRepo.save(accounts.getFrom());
        }

        if (!isNull(accounts.getTo())) {
            accounts.getTo().setBalance(accounts.getTo().getBalance().add(amount.multiply(BigDecimal.valueOf(rate))));
            accountRepo.save(accounts.getTo());
        }

        if (isDelete) {
            if (!isNull(accounts.getFrom())) {
                accounts.getFrom()
                        .setBalance(accounts.getFrom().getBalance().add(amount));
            }

            if (!isNull(accounts.getTo())) {
                accounts.getTo()
                        .setBalance(accounts.getTo().getBalance().subtract(amount.multiply(BigDecimal.valueOf(rate))));
            }
        }

    }

    @Override
    public List<AccountTypeRsModel> getAllAccountTypes() {
        List<AccountTypeRsModel> response = accountTypeRepo.findAll()
                .stream()
                .map(ACCOUNT_MAPPER_INSTANCE::buildAccountTypeResponseModel)
                .collect(Collectors.toList());

        log.info(ALL_ACCOUNT_TYPES_MSG, response);
        return response;
    }

    @Override
    public List<CurrencyRsModel> getAllCurrencies() {
        List<CurrencyRsModel> response = currencyRepo.findAll().stream()
                .map(ACCOUNT_MAPPER_INSTANCE::buildCurrencyResponseModel)
                .collect(Collectors.toList());

        log.info(ALL_CURRENCIES_MSG, response);
        return response;
    }

    @Override
    public Account byIdAndUser(String accountId, User user) {
        var account = accountRepo
                .byIdAndUser(accountId, user)
                .orElseThrow(() -> new DataNotFoundException(
                        format(UNAUTHORIZED_ACCOUNT_MSG, user.getUsername(), accountId), 2000));

        log.info(ACCOUNT_BY_ID_MSG, accountId, account);
        return account;
    }

    private Currency getCurrency(String currencyName) {
        var currency = currencyRepo
                .byName(currencyName)
                .orElseThrow(() -> new DataNotFoundException(format(CURRENCY_NOT_FOUND_MSG, currencyName), 2001));

        log.info(CURRENCY_BY_NAME_MSG, currencyName, currency);
        return currency;
    }

    private AccountType getAccountType(String accountTypeName, boolean isInitialAccount) {
        var accountType = accountTypeRepo
                .byName(isInitialAccount ? CASH_ACCOUNT : accountTypeName)
                .orElseThrow(() ->
                        new DataNotFoundException(format(ACCOUNT_TYPE_NOT_FOUND_MSG, accountTypeName), 2002));

        log.info(ACCOUNT_TYPE_BY_NAME_MSG, accountTypeName, accountType);
        return accountType;
    }

    private void updateAccountValues(UpdateAccountModel requestBody, Account account) {
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

    private void updateBalanceValue(UpdateBalanceRqModel requestBody, Account account) {
        account.setBalance(requestBody.getBalance());
        accountRepo.save(account);
    }

    private void checkDuplicateAccount(String accountName, User user) {
        if (accountRepo.byNameAndUser(accountName, user).isPresent()) {
            throw new DuplicateException(format(DUPLICATE_ACCOUNT_NAME_MSG, user.getUsername(), accountName), 2003);
        }
    }

    private void checkUpdateDuplicateAccount(String accountName, User user, String accountId) {
        if (accountRepo.byNameAndUserAndIdNot(accountName, user, accountId).isPresent()) {
            throw new DuplicateException(format(DUPLICATE_ACCOUNT_NAME_MSG, user.getUsername(), accountName), 2003);
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

    private void checkNegativeBalance(UpdateBalanceRqModel requestBody, Account account) {
        if (requestBody.getBalance().compareTo(BigDecimal.ZERO) < 0 && !account.isAllowNegative()) {
            throw new NotEnoughBalanceException(format(NEGATIVE_BALANCE_NOT_ALLOWED, account.getAccountId()));
        }
    }
}

