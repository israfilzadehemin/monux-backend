package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class AccountBuilder {

    private final AccountRepository accountRepo;

    public Account buildAccount(AccountRqModel requestBody, User user, AccountType accountType,
                                 Currency currency, boolean isInitialAccount) {
        return accountRepo.save(Account.builder()
                .accountId(UUID.randomUUID().toString())
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


}
