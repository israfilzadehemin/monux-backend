package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.CreateAccountModel;

public interface AccountService {
    CreateAccountModel createInitialAccount(CreateAccountModel createAccountRequestModel);

}
