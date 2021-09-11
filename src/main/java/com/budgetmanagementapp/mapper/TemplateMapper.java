package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Template;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;

@Mapper(componentModel = "spring")
public abstract class TemplateMapper {

    public static TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);

    @Mapping(source = "template.templateId", target = "transactionId")
    public abstract TransactionRsModel buildGenericResponseModel(Template template);

    @Mappings({
            @Mapping(source = "template.templateId", target = "transactionId"),
            @Mapping(source = "template.category.categoryId", target = "categoryId"),
            @Mapping(target = "accountId", ignore = true),
            @Mapping(target = "labelIds", ignore = true),

    })
    public abstract InOutRsModel buildInOutResponseModel(Template template);

    @AfterMapping
    void mapAccountIdAndLabelIds(@MappingTarget InOutRsModel.InOutRsModelBuilder<?, ?> inOutRsModel, Template template) {

        inOutRsModel.accountId(template.getType().equals(INCOME.name())
                ? template.getReceiverAccount().getAccountId()
                : template.getSenderAccount().getAccountId());

        inOutRsModel.labelIds(template.getLabels().stream()
                .map(Label::getLabelId).collect(Collectors.toList()));
    }


    @Mappings({
            @Mapping(source = "template.templateId", target = "transactionId"),
            @Mapping(source = "template.senderAccount.accountId", target = "senderAccountId"),
            @Mapping(source = "template.receiverAccount.accountId", target = "receiverAccountId"),
    })
    public abstract TransferRsModel buildTransferResponseModel(Template template);

    @Mapping(source = "template.templateId", target = "transactionId")
    public abstract DebtRsModel buildDebtResponseModel(Template template);

    @AfterMapping
    void mapAccountId(@MappingTarget DebtRsModel.DebtRsModelBuilder<?, ?> debtRsModel, Template template) {
        debtRsModel.accountId(
                template.getType().equals(DEBT_IN.name())
                        ? template.getReceiverAccount().getAccountId()
                        : template.getSenderAccount().getAccountId());
    }
}
