package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.CASH_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.ACCOUNT_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.ACCOUNT_NOT_FOUND_MSG;
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
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.AccountNotFoundException;
import com.budgetmanagementapp.exception.AccountTypeNotFoundException;
import com.budgetmanagementapp.exception.CurrencyNotFoundException;
import com.budgetmanagementapp.exception.DuplicateAccountException;
import com.budgetmanagementapp.exception.InitialAccountExistingException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.AccountRequestModel;
import com.budgetmanagementapp.model.AccountResponseModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.AccountTypeRepository;
import com.budgetmanagementapp.repository.CurrencyRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.List;
import java.util.UUID;
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
    private final UserRepository userRepo;
    private final AccountTypeRepository accountTypeRepo;
    private final CurrencyRepository currencyRepo;

    @Override
    @Transactional
    public AccountResponseModel createAccount(AccountRequestModel accountRequestModel, boolean isInitialAccount) {
        CustomValidator.validateAccountModel(accountRequestModel, isInitialAccount);

        User user = findUserByUsername(accountRequestModel.getUsername());

        if (isInitialAccount && !user.getAccounts().isEmpty()) {
            throw new InitialAccountExistingException(String.format(INITIAL_ACCOUNT_EXISTING_MSG, user.getUsername()));
        }

        if (accountRepo.findByNameAndUser(accountRequestModel.getAccountName(), user).isPresent()) {
            throw new DuplicateAccountException(
                    String.format(DUPLICATE_ACCOUNT_NAME_MSG,
                            user.getUsername(),
                            accountRequestModel.getAccountName()));
        }

        Account account = buildAccount(
                accountRequestModel,
                user,
                getAccountType(accountRequestModel.getAccountTypeName(), isInitialAccount),
                getCurrency(accountRequestModel),
                isInitialAccount
        );

        log.info(String.format(ACCOUNT_CREATED_MSG, user.getUsername(), buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountResponseModel updateAccount(UpdateAccountModel accountModel, String username) {
        CustomValidator.validateUpdateAccountModel(accountModel);

        Account account = accountRepo
                .findByAccountIdAndUser(accountModel.getAccountId(), findUserByUsername(username))
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format(UNAUTHORIZED_ACCOUNT_MSG, username, accountModel.getAccountId())));

        account.setIcon(accountModel.getIcon());
        account.setName(accountModel.getNewAccountName());
        account.setAccountType(getAccountType(accountModel.getAccountTypeName(), false));
        accountRepo.save(account);

        log.info(String.format(ACCOUNT_UPDATED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountResponseModel toggleAllowNegative(String accountId, String username) {
        CustomValidator.validateAccountId(accountId);

        Account account = accountRepo
                .findByAccountIdAndUser(accountId, findUserByUsername(username))
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format(UNAUTHORIZED_ACCOUNT_MSG, username, accountId)));

        account.setAllowNegative(!account.isAllowNegative());
        accountRepo.save(account);

        log.info(String.format(ALLOW_NEGATIVE_TOGGLED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public AccountResponseModel toggleShowInSum(String accountId, String username) {
        CustomValidator.validateAccountId(accountId);

        Account account = accountRepo
                .findByAccountIdAndUser(accountId, findUserByUsername(username))
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format(UNAUTHORIZED_ACCOUNT_MSG, username, accountId)));

        account.setShowInSum(!account.isShowInSum());
        accountRepo.save(account);

        log.info(String.format(SHOW_IN_SUM_TOGGLED_MSG, username, buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }

    @Override
    public List<AccountResponseModel> getAllAccountsByUser(String username) {
        User user = findUserByUsername(username);

        List<Account> accounts = accountRepo.findAllByUser(user);

        if (accounts.isEmpty()) {
            throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MSG,
                    user.getUsername()));
        }

        log.info(String.format(
                ALL_ACCOUNTS_MSG,
                user.getUsername(),
                accounts.stream().map(this::buildAccountResponseModel).collect(Collectors.toList())));
        return accounts.stream().map(this::buildAccountResponseModel).collect(Collectors.toList());
    }

    @Override
    public AccountResponseModel updateBalance(UpdateBalanceModel balanceModel, String username) {
        CustomValidator.validateUpdateBalanceModel(balanceModel);

        Account account = accountRepo
                .findByAccountIdAndUser(balanceModel.getAccountId(), findUserByUsername(username))
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format(UNAUTHORIZED_ACCOUNT_MSG, username, balanceModel.getAccountId())));

        account.setBalance(balanceModel.getBalance());
        accountRepo.save(account);

        log.info(String.format(BALANCE_UPDATED_MSG, username, account.getName(), buildAccountResponseModel(account)));
        return buildAccountResponseModel(account);
    }


    private User findUserByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    private Currency getCurrency(AccountRequestModel accountRequestModel) {
        return currencyRepo
                .findByName(accountRequestModel.getCurrency())
                .orElseThrow(() ->
                        new CurrencyNotFoundException(String.format(CURRENCY_NOT_FOUND_MSG,
                                accountRequestModel.getCurrency())));
    }

    private AccountType getAccountType(String accountTypeName, boolean isInitialAccount) {
        return accountTypeRepo
                .findByAccountTypeName(isInitialAccount ? CASH_ACCOUNT : accountTypeName)
                .orElseThrow(() ->
                        new AccountTypeNotFoundException(String.format(ACCOUNT_TYPE_NOT_FOUND_MSG, accountTypeName)));
    }

    private Account buildAccount(AccountRequestModel accountRequestModel, User user, AccountType accountType,
                                 Currency currency, boolean isInitialAccount) {
        return accountRepo.save(Account.builder()
                .accountId(UUID.randomUUID().toString())
                .icon(accountRequestModel.getIcon())
                .name(accountRequestModel.getAccountName())
                .accountType(accountType)
                .currency(currency)
                .allowNegative(isInitialAccount || accountRequestModel.getAllowNegative())
                .balance(accountRequestModel.getBalance())
                .enabled(true)
                .showInSum(isInitialAccount || accountRequestModel.getShowInSum())
                .user(user)
                .build());
    }

    private AccountResponseModel buildAccountResponseModel(Account account) {
        return AccountResponseModel.builder()
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
}

