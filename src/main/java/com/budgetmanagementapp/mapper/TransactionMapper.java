package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.model.account.DebtRsModel;
import com.budgetmanagementapp.model.account.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionRsModel buildGenericResponseModel(Transaction transaction);

    @Mapping(source = "transaction.category.categoryId", target = "categoryId")
    InOutRsModel buildInOutResponseModel(Transaction transaction);

    @AfterMapping
    default void mapAccountIdAndLabelId(@MappingTarget InOutRsModel inOutRsModel, Transaction transaction) {
        inOutRsModel.setAccountId(
                transaction.getType().equals(INCOME.name())
                        ? transaction.getReceiverAccount().getAccountId()
                        : transaction.getSenderAccount().getAccountId());

        inOutRsModel.setLabelIds(
                transaction.getLabels().stream()
                        .map(Label::getLabelId).collect(Collectors.toList())
        );
    }

    @Mappings({
            @Mapping(source = "transaction.senderAccount.accountId", target = "senderAccountId"),
            @Mapping(source = "transaction.receiverAccount.accountId", target = "receiverAccountId"),
    })
    TransferRsModel buildTransferResponseModel(Transaction transaction);


    @Mapping(source = "transaction.category.categoryId", target = "categoryId")
    DebtRsModel buildDebtResponseModel(Transaction transaction);

    @AfterMapping
    default void mapAccountId(@MappingTarget DebtRsModel debtRsModel, Transaction transaction) {
        debtRsModel.setAccountId(
                transaction.getType().equals(DEBT_IN.name())
                        ? transaction.getReceiverAccount().getAccountId()
                        : transaction.getSenderAccount().getAccountId());
    }
}
