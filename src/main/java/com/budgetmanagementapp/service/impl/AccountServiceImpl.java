package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.CASH_ACCOUNT;
import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.ACCOUNT_TYPE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CURRENCY_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INITIAL_ACCOUNT_EXISTING_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.AccountTypeNotFoundException;
import com.budgetmanagementapp.exception.CurrencyNotFoundException;
import com.budgetmanagementapp.exception.InitialAccountExistingException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.CreateAccountModel;
import com.budgetmanagementapp.repository.AccountRepository;
import com.budgetmanagementapp.repository.AccountTypeRepository;
import com.budgetmanagementapp.repository.CurrencyRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.UUID;
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
    public CreateAccountModel createInitialAccount(CreateAccountModel createAccountModel) {
        CustomValidator.validateInitialAccount(createAccountModel);

        User user =
                userRepo.findByUsernameAndStatus(createAccountModel.getUsername(), STATUS_ACTIVE)
                        .orElseThrow(() ->
                                new UserNotFoundException(String.format(USER_NOT_FOUND_MSG,
                                        createAccountModel.getUsername())));

        if (!user.getAccounts().isEmpty()) {
            throw new InitialAccountExistingException(String.format(INITIAL_ACCOUNT_EXISTING_MSG, user.getUsername()));
        }

        AccountType accountType = accountTypeRepo.findByAccountTypeName(CASH_ACCOUNT)
                .orElseThrow(() ->
                        new AccountTypeNotFoundException(String.format(ACCOUNT_TYPE_NOT_FOUND_MSG,
                                createAccountModel.getAccountTypeName())));

        Currency currency = currencyRepo.findByName(createAccountModel.getCurrency())
                .orElseThrow(() ->
                        new CurrencyNotFoundException(String.format(CURRENCY_NOT_FOUND_MSG,
                                createAccountModel.getCurrency())));

        Account account = buildAccount(createAccountModel, user, accountType, currency);

        return CreateAccountModel.builder()
                .username(account.getUser().getUsername())
                .icon(account.getIcon())
                .accountName(account.getName())
                .accountTypeName(account.getAccountType().getAccountTypeName())
                .currency(account.getCurrency().getName())
                .allowNegative(account.isAllowNegative())
                .balance(account.getBalance())
                .showInSum(account.isShowInSum())
                .build();
    }

    private Account buildAccount(CreateAccountModel createAccountModel, User user, AccountType accountType,
                                 Currency currency) {
        return accountRepo.save(Account.builder()
                .accountId(UUID.randomUUID().toString())
                .icon(createAccountModel.getIcon())
                .name(createAccountModel.getAccountName())
                .accountType(accountType)
                .currency(currency)
                .allowNegative(true)
                .balance(createAccountModel.getBalance())
                .enabled(true)
                .showInSum(true)
                .user(user)
                .build());
    }
}

