package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.CASH_ACCOUNT;
import static com.budgetmanagementapp.utility.MsgConstant.ACCOUNT_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.ACCOUNT_TYPE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.ACCOUNT_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.ALLOW_NEGATIVE_TOGGLED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_ACCOUNTS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.BALANCE_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CURRENCY_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DUPLICATE_ACCOUNT_NAME_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INITIAL_ACCOUNT_EXISTING_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.SHOW_IN_SUM_TOGGLED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_ACCOUNT_MSG;
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
import com.budgetmanagementapp.model.AccountRqModel;
import com.budgetmanagementapp.model.AccountRsModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.AccountTypeRepository;
import com.budgetmanagementapp.repository.CurrencyRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.List;
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
    public AccountRsModel createAccount(AccountRqModel accountRqModel, boolean isInitialAccount) {
        CustomValidator.validateAccountModel(accountRqModel, isInitialAccount);

        User user = userService.findByUsername(accountRqModel.getUsername());
        checkInitialAccountExistence(isInitialAccount, user);
        checkDuplicateAccount(accountRqModel.getAccountName(), user);

        Account account = buildAccount(
                accountRqModel,
                user,
                getAccountType(accountRqModel.getAccountTypeName(), isInitialAccount),
                getCurrency(accountRqModel.getCurrency()),
                isInitialAccount
        );

        log.info(format(ACCOUNT_CREATED_MSG, user.getUsername(), buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel updateAccount(UpdateAccountModel accountModel, String username) {
        Account account = accountByIdAndUser(accountModel.getAccountId(), userService.findByUsername(username));
        updateAccountValues(accountModel, account);

        log.info(format(ACCOUNT_UPDATED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountRsModel toggleAllowNegative(String accountId, String username) {
        Account account = accountByIdAndUser(accountId, userService.findByUsername(username));
        toggleAllowNegativeValue(account);

        log.info(format(ALLOW_NEGATIVE_TOGGLED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }


    @Override
    public AccountRsModel toggleShowInSum(String accountId, String username) {
        Account account = accountByIdAndUser(accountId, userService.findByUsername(username));
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
    public AccountRsModel updateBalance(UpdateBalanceModel balanceModel, String username) {
        Account account = accountByIdAndUser(balanceModel.getAccountId(), userService.findByUsername(username));
        updateBalanceValue(balanceModel, account);

        log.info(format(BALANCE_UPDATED_MSG, username, account.getName(), buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    private Account buildAccount(AccountRqModel accountRqModel, User user, AccountType accountType,
                                 Currency currency, boolean isInitialAccount) {
        return accountRepo.save(Account.builder()
                .accountId(UUID.randomUUID().toString())
                .icon(accountRqModel.getIcon())
                .name(accountRqModel.getAccountName())
                .accountType(accountType)
                .currency(currency)
                .allowNegative(isInitialAccount || accountRqModel.getAllowNegative())
                .balance(accountRqModel.getBalance())
                .enabled(true)
                .showInSum(isInitialAccount || accountRqModel.getShowInSum())
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

    private Account accountByIdAndUser(String accountId, User user) {
        return accountRepo
                .byIdAndUser(accountId, user)
                .orElseThrow(() -> new AccountNotFoundException(
                        format(UNAUTHORIZED_ACCOUNT_MSG, user.getUsername(), accountId)));
    }

    private Currency getCurrency(String currency) {
        return currencyRepo
                .findByName(currency)
                .orElseThrow(() -> new CurrencyNotFoundException(format(CURRENCY_NOT_FOUND_MSG, currency)));
    }

    private AccountType getAccountType(String accountTypeName, boolean isInitialAccount) {
        return accountTypeRepo
                .findByAccountTypeName(isInitialAccount ? CASH_ACCOUNT : accountTypeName)
                .orElseThrow(() ->
                        new AccountTypeNotFoundException(format(ACCOUNT_TYPE_NOT_FOUND_MSG, accountTypeName)));
    }

    private void updateAccountValues(UpdateAccountModel accountModel, Account account) {
        account.setIcon(accountModel.getIcon());
        account.setName(accountModel.getNewAccountName());
        account.setAccountType(getAccountType(accountModel.getAccountTypeName(), false));
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

    private void updateBalanceValue(UpdateBalanceModel balanceModel, Account account) {
        account.setBalance(balanceModel.getBalance());
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

}

