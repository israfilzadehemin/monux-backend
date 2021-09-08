package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.model.account.DebtRqModel;
import com.budgetmanagementapp.model.account.InOutRqModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.TransactionType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;

@Component
public class TemplateBuilder {
    public Template buildTemplate(InOutRqModel requestBody, User user, Category category,
                                   List<Label> labels, TransactionType type) {
        return Template.builder()
                .templateId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .category(category)
                .labels(labels)
                .user(user)
                .build();
    }

    public Template buildTemplate(TransferRqModel requestBody, User user,
                                   Account senderAccount,
                                   Account receiverAccount) {
        return Template.builder()
                .templateId(UUID.randomUUID().toString())
                .type(TRANSFER.name())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .user(user)
                .build();
    }

    public Template buildTemplate(DebtRqModel requestBody,
                                   TransactionType type,
                                   User user) {
        return Template.builder()
                .templateId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .user(user)
                .build();
    }



    }
