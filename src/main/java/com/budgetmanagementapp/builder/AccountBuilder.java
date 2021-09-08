package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.AccountRqModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountBuilder {

    public Account buildAccount(AccountRqModel requestBody, User user, AccountType accountType,
                                 Currency currency, boolean isInitialAccount) {
        return Account.builder()
                .accountId(UUID.randomUUID().toString())
                .name(requestBody.getAccountName())
                .accountType(accountType)
                .currency(currency)
                .allowNegative(isInitialAccount || requestBody.getAllowNegative())
                .balance(requestBody.getBalance())
                .enabled(true)
                .showInSum(isInitialAccount || requestBody.getShowInSum())
                .user(user)
                .build();
    }


}
