package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Template;
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
public interface TemplateMapper {

    TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);

    @Mapping(source = "template.templateId", target = "transactionId")
    TransactionRsModel buildGenericResponseModel(Template template);

    @Mappings({
            @Mapping(source = "template.templateId", target = "transactionId"),
            @Mapping(source = "template.category.categoryId", target = "categoryId"),

    })
    InOutRsModel buildInOutResponseModel(Template template);

    @AfterMapping
    default void mapAccountIdAndLabelIds(@MappingTarget InOutRsModel inOutRsModel, Template template) {
        inOutRsModel.setAccountId(
                template.getType().equals(INCOME.name())
                        ? template.getReceiverAccount().getAccountId()
                        : template.getSenderAccount().getAccountId());

        inOutRsModel.setLabelIds(
                template.getLabels().stream()
                        .map(Label::getLabelId).collect(Collectors.toList()));
    }


    @Mappings({
            @Mapping(source = "template.templateId", target = "transactionId"),
            @Mapping(source = "template.senderAccount.accountId", target = "senderAccountId"),
            @Mapping(source = "template.receiverAccount.accountId", target = "receiverAccountId"),
    })
    TransferRsModel buildTransferResponseModel(Template template);

    @Mapping(source = "template.templateId", target = "transactionId")
    DebtRsModel buildDebtResponseModel(Template template);

    @AfterMapping
    default void mapAccountId(@MappingTarget DebtRsModel debtRsModel, Template template) {
        debtRsModel.setAccountId(
                template.getType().equals(DEBT_IN.name())
                        ? template.getReceiverAccount().getAccountId()
                        : template.getSenderAccount().getAccountId());
    }
}
