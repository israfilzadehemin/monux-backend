package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.AccountRqModel;
import com.budgetmanagementapp.model.AccountRsModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
import java.util.List;

public interface AccountService {
    AccountRsModel createAccount(AccountRqModel createAccountRqModel, boolean isInitialAccount);

    AccountRsModel updateAccount(UpdateAccountModel accountModel, String username);

    AccountRsModel toggleAllowNegative(String accountId, String username);

    AccountRsModel toggleShowInSum(String accountId, String username);

    List<AccountRsModel> getAllAccountsByUser(String username);

    AccountRsModel updateBalance(UpdateBalanceModel balanceModel, String username);
}
