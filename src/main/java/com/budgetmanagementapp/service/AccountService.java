package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.account.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {
    AccountRsModel createAccount(AccountRqModel createAccountRqModel, boolean isInitialAccount);

    AccountRsModel updateAccount(UpdateAccountModel accountModel, String username);

    AccountRsModel toggleAllowNegative(String accountId, String username);

    AccountRsModel toggleShowInSum(String accountId, String username);

    List<AccountRsModel> getAllAccountsByUser(String username);

    AccountRsModel updateBalance(UpdateBalanceModel balanceModel, String username);

    Account byIdAndUser(String accountId, User user);

    void updateBalanceByRate(BigDecimal amount, Double rate, Map<String, Account> accounts);

    void updateBalanceForTransferDelete(BigDecimal amount, Double rate, Map<String, Account> accounts);

    List<AccountTypeRsModel> getAllAccountTypes();

    List<CurrencyRsModel> getAllCurrencies();
}
