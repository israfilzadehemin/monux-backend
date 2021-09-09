package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.mapper.TemplateMapper;
import com.budgetmanagementapp.model.account.DebtRqModel;
import com.budgetmanagementapp.model.account.InOutRqModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.repository.TemplateRepository;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.TransactionType.*;

@AllArgsConstructor
@Component
public class TemplateBuilder {

    private final TemplateRepository templateRepo;

    public Template buildTemplate(InOutRqModel requestBody, User user, Category category,
                                   List<Label> labels, TransactionType type, Account account) {
        Template template = Template.builder()
                .templateId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .category(category)
                .labels(labels)
                .user(user)
                .build();
        if (type.equals(INCOME)) {
            template.setReceiverAccount(account);
        } else {
            template.setSenderAccount(account);
        }
        return templateRepo.save(template);
    }

    public Template buildTemplate(TransferRqModel requestBody, User user,
                                   Account senderAccount,
                                   Account receiverAccount) {
        return templateRepo.save(Template.builder()
                .templateId(UUID.randomUUID().toString())
                .type(TRANSFER.name())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .user(user)
                .build());
    }

    public Template buildTemplate(DebtRqModel requestBody,
                                   TransactionType type,
                                   User user, Account account) {
        Template template = Template.builder()
                .templateId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .user(user)
                .build();

        if (type.equals(DEBT_IN)) {
            template.setReceiverAccount(account);
        } else {
            template.setSenderAccount(account);
        }
        return templateRepo.save(template);
    }

    public TransactionRsModel buildGenericResponseModel(Template template) {
        TransactionRsModel response = TemplateMapper.INSTANCE.buildGenericResponseModel(template);

        if (!Objects.isNull(template.getSenderAccount())) {
            response.setSenderAccountId(template.getSenderAccount().getAccountId());
        }
        if (!Objects.isNull(template.getReceiverAccount())) {
            response.setReceiverAccountId(template.getReceiverAccount().getAccountId());
        }
        if (!Objects.isNull(template.getCategory())) {
            response.setCategoryId(template.getCategory().getCategoryId());
        }
        if (!Objects.isNull(template.getLabels())) {
            response.setLabelIds(template.getLabels().stream().map(Label::getLabelId).collect(Collectors.toList()));
        }
        return response;
    }

}
