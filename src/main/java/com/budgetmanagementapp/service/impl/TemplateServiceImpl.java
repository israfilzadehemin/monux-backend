package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static com.budgetmanagementapp.utility.TransactionType.valueOf;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.exception.TemplateNotFoundException;
import com.budgetmanagementapp.exception.TransactionNotFoundException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.model.DebtRqModel;
import com.budgetmanagementapp.model.DebtRsModel;
import com.budgetmanagementapp.model.InOutRqModel;
import com.budgetmanagementapp.model.InOutRsModel;
import com.budgetmanagementapp.model.TransactionRsModel;
import com.budgetmanagementapp.model.TransferRqModel;
import com.budgetmanagementapp.model.TransferRsModel;
import com.budgetmanagementapp.model.UpdateDebtRqModel;
import com.budgetmanagementapp.model.UpdateInOutRqModel;
import com.budgetmanagementapp.model.UpdateTransferRqModel;
import com.budgetmanagementapp.repository.TemplateRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.CategoryService;
import com.budgetmanagementapp.service.LabelService;
import com.budgetmanagementapp.service.TemplateService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.TransactionType;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final UserService userService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final LabelService labelService;
    private final TemplateRepository templateRepo;


    @Override
    public TransactionRsModel createTemplate(InOutRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category = categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), type.name(), user);

        Template template = buildTemplate(requestBody, user, account, category, labels, type);

        InOutRsModel response = buildInOutResponseModel(template);
        log.info(format(IN_OUT_TEMPLATE_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public TransferRsModel createTemplate(TransferRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account senderAccount = accountService.byIdAndUser(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountService.byIdAndUser(requestBody.getReceiverAccountId(), user);

        if (requestBody.getReceiverAccountId().equals(requestBody.getSenderAccountId())) {
            throw new TransferToSelfException(TRANSFER_TO_SELF_MSG);
        }

        Template template = buildTemplate(requestBody, user, senderAccount, receiverAccount);

        TransferRsModel response = buildTransferResponseModel(template);
        log.info(format(TRANSFER_TEMPLATE_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public DebtRsModel createTemplate(DebtRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);

        Template template = buildTemplate(requestBody, type, user, account);

        DebtRsModel response = buildDebtResponseModel(template);
        log.info(format(DEBT_TEMPLATE_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public InOutRsModel updateTemplate(UpdateInOutRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Template template = templateByIdAndUser(requestBody.getTransactionId(), user);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category =
                categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), valueOf(template.getType()), user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), template.getType(), user);

        Template updatedTemplate = updateTemplateValues(requestBody, template, account, category, labels);

        InOutRsModel response = buildInOutResponseModel(updatedTemplate);
        log.info(format(IN_OUT_TEMPLATE_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public TransferRsModel updateTemplate(UpdateTransferRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Template templateByIdAndUser = templateByIdAndUser(requestBody.getTransactionId(), user);
        Account senderAccount = accountService.byIdAndUser(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountService.byIdAndUser(requestBody.getReceiverAccountId(), user);

        if (requestBody.getReceiverAccountId().equals(requestBody.getSenderAccountId())) {
            throw new TransferToSelfException(TRANSFER_TO_SELF_MSG);
        }

        Template updatedTemplate =
                updateTemplateValues(requestBody, templateByIdAndUser, senderAccount, receiverAccount);

        TransferRsModel response = buildTransferResponseModel(updatedTemplate);
        log.info(format(TRANSFER_TEMPLATE_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public DebtRsModel updateTemplate(UpdateDebtRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Template transaction = templateByIdAndUser(requestBody.getTransactionId(), user);

        Template updatedTemplate = updateTemplateValues(requestBody, account, transaction);

        DebtRsModel response = buildDebtResponseModel(updatedTemplate);
        log.info(format(DEBT_TEMPLATE_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public List<TransactionRsModel> getAllTemplatesByUser(String username) {
        List<TransactionRsModel> response =
                templateRepo.allByUser(userService.findByUsername(username))
                        .stream()
                        .map(this::buildGenericResponseModel)
                        .collect(Collectors.toList());

        log.info(String.format(ALL_TEMPLATES_MSG, username, response));
        return response;
    }

    @Transactional
    @Override
    public List<TransactionRsModel> deleteTemplateById(String username, List<String> templateIds) {
        User user = userService.findByUsername(username);
        List<Template> deletedTemplates = templateRepo.deleteById(user, templateIds);

        log.info(String.format(DELETED_TEMPLATES_MSG, username, deletedTemplates));
        return deletedTemplates.stream()
                .map(this::buildGenericResponseModel)
                .collect(Collectors.toList());
    }

    private Template buildTemplate(InOutRqModel requestBody, User user,
                                   Account account, Category category,
                                   List<Label> labels, TransactionType type) {
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

    private Template buildTemplate(TransferRqModel requestBody, User user,
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

    private Template buildTemplate(DebtRqModel requestBody,
                                   TransactionType type,
                                   User user,
                                   Account account) {
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

    private Template updateTemplateValues(UpdateInOutRqModel requestBody, Template template,
                                          Account account, Category category,
                                          List<Label> labels) {
        template.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        template.setAmount(requestBody.getAmount());
        template.setDescription(requestBody.getDescription());
        template.setCategory(category);
        template.setLabels(labels);

        if (template.getType().equals(INCOME.name())) {
            template.setReceiverAccount(account);
        } else {
            template.setSenderAccount(account);
        }

        return templateRepo.save(template);
    }

    private Template updateTemplateValues(UpdateTransferRqModel requestBody,
                                          Template template,
                                          Account senderAccount,
                                          Account receiverAccount) {
        template.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        template.setAmount(requestBody.getAmount());
        template.setDescription(requestBody.getDescription());
        template.setSenderAccount(senderAccount);
        template.setReceiverAccount(receiverAccount);
        return templateRepo.save(template);
    }

    private Template updateTemplateValues(UpdateDebtRqModel requestBody,
                                          Account account,
                                          Template template) {
        template.setDateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()));
        template.setAmount(requestBody.getAmount());
        template.setDescription(requestBody.getDescription());

        if (template.getType().equals(DEBT_IN.name())) {
            template.setReceiverAccount(account);
        } else {
            template.setSenderAccount(account);
        }

        return templateRepo.save(template);
    }

    private TransactionRsModel buildGenericResponseModel(Template template) {
        TransactionRsModel response = TransactionRsModel.builder()
                .transactionId(template.getTemplateId())
                .dateTime(template.getDateTime())
                .amount(template.getAmount())
                .description(template.getDescription())
                .type(template.getType())
                .build();

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


    private InOutRsModel buildInOutResponseModel(Template template) {
        return InOutRsModel.builder()
                .transactionId(template.getTemplateId())
                .dateTime(template.getDateTime())
                .amount(template.getAmount())
                .description(template.getDescription())
                .type(template.getType())
                .accountId(
                        template.getType().equals(INCOME.name())
                                ? template.getReceiverAccount().getAccountId()
                                : template.getSenderAccount().getAccountId())
                .categoryId(template.getCategory().getCategoryId())
                .labelIds(template.getLabels().stream().map(Label::getLabelId).collect(Collectors.toList()))
                .build();
    }

    private TransferRsModel buildTransferResponseModel(Template template) {
        return TransferRsModel.builder()
                .transactionId(template.getTemplateId())
                .dateTime(template.getDateTime())
                .amount(template.getAmount())
                .description(template.getDescription())
                .senderAccountId(template.getSenderAccount().getAccountId())
                .receiverAccountId(template.getReceiverAccount().getAccountId())
                .type(template.getType())
                .build();
    }

    private DebtRsModel buildDebtResponseModel(Template ttemplate) {
        return DebtRsModel.builder()
                .transactionId(ttemplate.getTemplateId())
                .dateTime(ttemplate.getDateTime())
                .amount(ttemplate.getAmount())
                .description(ttemplate.getDescription())
                .type(ttemplate.getType())
                .accountId(
                        ttemplate.getType().equals(DEBT_IN.name())
                                ? ttemplate.getReceiverAccount().getAccountId()
                                : ttemplate.getSenderAccount().getAccountId())
                .build();
    }

    private Template templateByIdAndUser(String templateId, User user) {
        return templateRepo.byIdAndUser(templateId, user)
                .orElseThrow(() ->
                        new TemplateNotFoundException(
                                format(UNAUTHORIZED_TEMPLATE_MSG, user.getUsername(), templateId)));
    }

}
