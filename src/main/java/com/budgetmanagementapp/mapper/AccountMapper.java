package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.model.AccountRsModel;
import com.budgetmanagementapp.model.AccountTypeRsModel;
import com.budgetmanagementapp.model.CurrencyRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "accountName"),
            @Mapping(target = "accountTypeName", expression = "java(account.getAccountType().getAccountTypeName())"),
            @Mapping(target = "currency", expression = "java(account.getCurrency().getName())")
    })
    AccountRsModel buildAccountResponseModel(Account account);

    AccountTypeRsModel buildAccountTypeResponseModel(AccountType accountType);

    @Mapping(source = "name", target = "currencyName")
    CurrencyRsModel buildCurrencyResponseModel(Currency currency);

}
