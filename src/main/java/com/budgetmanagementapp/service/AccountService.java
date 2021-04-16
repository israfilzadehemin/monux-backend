package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.AccountRequestModel;
import com.budgetmanagementapp.model.AccountResponseModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
import java.util.List;

public interface AccountService {
    AccountResponseModel createAccount(AccountRequestModel createAccountRequestModel, boolean isInitialAccount);

    AccountResponseModel updateAccount(UpdateAccountModel accountModel, String username);

    AccountResponseModel toggleAllowNegative(String accountId, String username);

    AccountResponseModel toggleShowInSum(String accountId, String username);

    List<AccountResponseModel> getAllAccountsByUser(String username);

    AccountResponseModel updateBalance(UpdateBalanceModel balanceModel, String username);
}
