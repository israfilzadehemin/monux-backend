package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.model.account.AccountRsModel;
import com.budgetmanagementapp.model.account.AccountTypeRsModel;
import com.budgetmanagementapp.model.account.CurrencyRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "accountName"),
            @Mapping(target = "currency", source = "account.currency.name")
    })
    AccountRsModel buildAccountResponseModel(Account account);

    @AfterMapping
    default void addExtraFields(@MappingTarget AccountRsModel accountRsModel, Account account) {
        accountRsModel.setAccountTypeName(account.getAccountType().getAccountTypeName());
    }

    AccountTypeRsModel buildAccountTypeResponseModel(AccountType accountType);

    @Mapping(source = "name", target = "currencyName")
    CurrencyRsModel buildCurrencyResponseModel(Currency currency);

}
