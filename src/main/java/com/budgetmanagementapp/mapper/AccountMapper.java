package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.model.account.AccountRsModel;
import com.budgetmanagementapp.model.account.AccountTypeRsModel;
import com.budgetmanagementapp.model.account.CurrencyRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AccountMapper {

    public static AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mappings({
            @Mapping(target = "accountName", source = "name"),
            @Mapping(target = "accountTypeName", source = "account.accountType.accountTypeName"),
            @Mapping(target = "currency", source = "account.currency.name")
    })
    public abstract AccountRsModel buildAccountResponseModel(Account account);

    public abstract AccountTypeRsModel buildAccountTypeResponseModel(AccountType accountType);

    @Mapping(source = "name", target = "currencyName")
    public abstract CurrencyRsModel buildCurrencyResponseModel(Currency currency);

}
