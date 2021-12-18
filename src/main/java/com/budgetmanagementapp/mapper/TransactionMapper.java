package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;

@Mapper
public abstract class TransactionMapper {

    public static TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mappings({
            @Mapping(target = "senderAccountId", ignore = true),
            @Mapping(target = "receiverAccountId", ignore = true),
            @Mapping(target = "labelIds", ignore = true),

    })
    public abstract TransactionRsModel buildGenericResponseModel(Transaction transaction);

    @AfterMapping
    void mapGenericResponseModel(@MappingTarget TransactionRsModel.TransactionRsModelBuilder<?, ?> transactionRsModel, Transaction transaction) {

        if (!Objects.isNull(transaction.getSenderAccount())) {
            transactionRsModel.senderAccountId(transaction.getSenderAccount().getAccountId());
        }
        if (!Objects.isNull(transaction.getReceiverAccount())) {
            transactionRsModel.receiverAccountId(transaction.getReceiverAccount().getAccountId());
        }
        if (!Objects.isNull(transaction.getCategory())) {
            transactionRsModel.categoryId(transaction.getCategory().getCategoryId());
        }
        if (!Objects.isNull(transaction.getLabels())) {
            transactionRsModel.labelIds(transaction.getLabels().stream().map(Label::getLabelId).collect(Collectors.toList()));
        }
    }

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
