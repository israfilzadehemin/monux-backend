package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionRsModel buildGenericResponseModel(Transaction transaction);

}
