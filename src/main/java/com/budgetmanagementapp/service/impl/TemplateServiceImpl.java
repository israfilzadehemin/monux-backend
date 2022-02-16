package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.builder.TemplateBuilder;
import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.model.transaction.*;
import com.budgetmanagementapp.model.transaction.TransferRqModel;
import com.budgetmanagementapp.model.transaction.TransferRsModel;
import com.budgetmanagementapp.repository.TemplateRepository;
import com.budgetmanagementapp.service.*;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.TransactionType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.mapper.TemplateMapper.TEMPLATE_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.*;
import static java.lang.String.format;

@Service
@Log4j2
@AllArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final UserService userService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final LabelService labelService;
    private final TemplateRepository templateRepo;
    private final TemplateBuilder templateBuilder;

    @Override
    public TransactionRsModel createTemplate(InOutRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category = categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), type.name(), user);

        Template template = templateRepo.save(
                templateBuilder.buildTemplate(requestBody, user, category, labels, type, account));

        InOutRsModel inOutRsModel = TEMPLATE_MAPPER_INSTANCE.buildInOutResponseModel(template);

        log.info(IN_OUT_TEMPLATE_CREATED_MSG, user.getUsername(), inOutRsModel);
        return inOutRsModel;
    }

    @Override
    public TransferRsModel createTemplate(TransferRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account senderAccount = accountService.byIdAndUser(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountService.byIdAndUser(requestBody.getReceiverAccountId(), user);

        if (requestBody.getReceiverAccountId().equals(requestBody.getSenderAccountId())) {
            throw new TransferToSelfException(TRANSFER_TO_SELF_MSG);
        }

        Template template = templateRepo.save(
                templateBuilder.buildTemplate(requestBody, user, senderAccount, receiverAccount));

        TransferRsModel transferRsModel = TEMPLATE_MAPPER_INSTANCE.buildTransferResponseModel(template);

        log.info(TRANSFER_TEMPLATE_CREATED_MSG, user.getUsername(), transferRsModel);
        return transferRsModel;
    }

    @Override
    public DebtRsModel createTemplate(DebtRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);

        Template template = templateRepo.save(templateBuilder.buildTemplate(requestBody, type, user, account));

        DebtRsModel debtRsModel = TEMPLATE_MAPPER_INSTANCE.buildDebtResponseModel(template);

        log.info(DEBT_TEMPLATE_CREATED_MSG, user.getUsername(), debtRsModel);
        return debtRsModel;
    }

    @Override
    public InOutRsModel updateTemplate(InOutRqModel requestBody, String templateId, String username) {
        User user = userService.findByUsername(username);
        Template template = templateByIdAndUser(templateId, user);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category =
                categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), valueOf(template.getType()), user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), template.getType(), user);

        Template updatedTemplate = updateTemplateValues(requestBody, template, account, category, labels);

        InOutRsModel inOutRsModel = TEMPLATE_MAPPER_INSTANCE.buildInOutResponseModel(updatedTemplate);

        log.info(IN_OUT_TEMPLATE_UPDATED_MSG, user.getUsername(), inOutRsModel);
        return inOutRsModel;
    }

    @Override
    public TransferRsModel updateTemplate(TransferRqModel requestBody, String templateId, String username) {
        User user = userService.findByUsername(username);
        Template templateByIdAndUser = templateByIdAndUser(templateId, user);
        Account senderAccount = accountService.byIdAndUser(requestBody.getSenderAccountId(), user);
        Account receiverAccount = accountService.byIdAndUser(requestBody.getReceiverAccountId(), user);

        if (requestBody.getReceiverAccountId().equals(requestBody.getSenderAccountId())) {
            throw new TransferToSelfException(TRANSFER_TO_SELF_MSG);
        }

        Template updatedTemplate =
                updateTemplateValues(requestBody, templateByIdAndUser, senderAccount, receiverAccount);

        TransferRsModel transferRsModel = TEMPLATE_MAPPER_INSTANCE.buildTransferResponseModel(updatedTemplate);

        log.info(TRANSFER_TEMPLATE_UPDATED_MSG, user.getUsername(), transferRsModel);
        return transferRsModel;
    }

    @Override
    public DebtRsModel updateTemplate(DebtRqModel requestBody, String templateId, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Template transaction = templateByIdAndUser(templateId, user);

        Template updatedTemplate = updateTemplateValues(requestBody, account, transaction);

        DebtRsModel debtRsModel = TEMPLATE_MAPPER_INSTANCE.buildDebtResponseModel(updatedTemplate);
        log.info(DEBT_TEMPLATE_UPDATED_MSG, user.getUsername(), debtRsModel);
        return debtRsModel;
    }

    @Override
    public List<TransactionRsModel> getAllTemplatesByUser(String username) {
        List<TransactionRsModel> transactionRsModels =
                templateRepo.allByUser(userService.findByUsername(username))
                        .stream()
                        .map(TEMPLATE_MAPPER_INSTANCE::buildGenericResponseModel)
                        .collect(Collectors.toList());

        log.info(ALL_TEMPLATES_MSG, username, transactionRsModels);
        return transactionRsModels;
    }

    @Transactional
    @Override
    public List<TransactionRsModel> deleteTemplateById(String username, List<String> templateIds) {
        User user = userService.findByUsername(username);
        List<Template> deletedTemplates = templateRepo.deleteByIdList(user, templateIds);

        var transactionRsModels = deletedTemplates
                .stream()
                .map(TEMPLATE_MAPPER_INSTANCE::buildGenericResponseModel)
                .collect(Collectors.toList());

        log.info(DELETED_TEMPLATES_MSG, username, transactionRsModels);
        return transactionRsModels;
    }

    private Template updateTemplateValues(InOutRqModel requestBody, Template template,
                                          Account account, Category category, List<Label> labels) {

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

    private Template updateTemplateValues(TransferRqModel requestBody,
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

    private Template updateTemplateValues(DebtRqModel requestBody, Account account, Template template) {
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

    private Template templateByIdAndUser(String templateId, User user) {
        var template = templateRepo.byIdAndUser(templateId, user)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                format(UNAUTHORIZED_TEMPLATE_MSG, user.getUsername(), templateId), 5005));

        log.info(TEMPLATE_BY_ID_USER_MSG, templateId, user, template);
        return template;
    }

}
