package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Template;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transaction.TransferRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;

@Mapper
public abstract class TemplateMapper {

    public static final TemplateMapper TEMPLATE_MAPPER_INSTANCE = Mappers.getMapper(TemplateMapper.class);

    @Mappings({
            @Mapping(source = "template.templateId", target = "transactionId"),
            @Mapping(target = "senderAccountId", ignore = true),
            @Mapping(target = "receiverAccountId", ignore = true),
            @Mapping(target = "labelIds", ignore = true),

    })
    public abstract TransactionRsModel buildGenericResponseModel(Template template);

    @AfterMapping
    void mapGenericResponseModel(@MappingTarget TransactionRsModel.TransactionRsModelBuilder<?, ?> templateRsModel, Template template) {

        if (!Objects.isNull(template.getSenderAccount())) {
            templateRsModel.senderAccountId(template.getSenderAccount().getAccountId());
        }
        if (!Objects.isNull(template.getReceiverAccount())) {
            templateRsModel.receiverAccountId(template.getReceiverAccount().getAccountId());
        }
        if (!Objects.isNull(template.getCategory())) {
            templateRsModel.categoryId(template.getCategory().getCategoryId());
        }
        if (!Objects.isNull(template.getLabels())) {
            templateRsModel.labelIds(template.getLabels().stream().map(Label::getLabelId).collect(Collectors.toList()));
        }
    }


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
