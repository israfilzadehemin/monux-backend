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
public abstract class TransactionMapper {

    public static TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    public abstract TransactionRsModel buildGenericResponseModel(Transaction transaction);

    @Mapping(source = "transaction.category.categoryId", target = "categoryId")
    public abstract InOutRsModel buildInOutResponseModel(Transaction transaction);

    @AfterMapping
    void mapAccountIdAndLabelId(@MappingTarget InOutRsModel.InOutRsModelBuilder<?, ?> inOutRsModel, Transaction transaction) {

        inOutRsModel.accountId(
                transaction.getType().equals(INCOME.name())
                        ? transaction.getReceiverAccount().getAccountId()
                        : transaction.getSenderAccount().getAccountId());

        inOutRsModel.labelIds(
                transaction.getLabels().stream()
                        .map(Label::getLabelId).collect(Collectors.toList())
        );
    }

    @Mappings({
            @Mapping(source = "transaction.senderAccount.accountId", target = "senderAccountId"),
            @Mapping(source = "transaction.receiverAccount.accountId", target = "receiverAccountId"),
    })
    public abstract TransferRsModel buildTransferResponseModel(Transaction transaction);


    @Mapping(source = "transaction.category.categoryId", target = "categoryId")
    public abstract DebtRsModel buildDebtResponseModel(Transaction transaction);

    @AfterMapping
    void mapAccountId(@MappingTarget DebtRsModel.DebtRsModelBuilder<?, ?> debtRsModel, Transaction transaction) {
        debtRsModel.accountId(
                transaction.getType().equals(DEBT_IN.name())
                        ? transaction.getReceiverAccount().getAccountId()
                        : transaction.getSenderAccount().getAccountId());
    }
}
