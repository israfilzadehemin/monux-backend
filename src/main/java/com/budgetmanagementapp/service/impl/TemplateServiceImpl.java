package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.valueOf;
import static java.lang.String.format;

import com.budgetmanagementapp.builder.TemplateBuilder;
import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.exception.TemplateNotFoundException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.mapper.TemplateMapper;
import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
import com.budgetmanagementapp.repository.TemplateRepository;
import com.budgetmanagementapp.service.AccountService;
import com.budgetmanagementapp.service.CategoryService;
import com.budgetmanagementapp.service.LabelService;
import com.budgetmanagementapp.service.TemplateService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.TransactionType;
import java.util.List;
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
    private final TemplateBuilder templateBuilder;


    @Override
    public TransactionRsModel createTemplate(InOutRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category = categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), type.name(), user);

        Template template = templateBuilder.buildTemplate(requestBody, user, category, labels, type, account);

        InOutRsModel response = TemplateMapper.INSTANCE.buildInOutResponseModel(template);
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

        Template template = templateBuilder.buildTemplate(requestBody, user, senderAccount, receiverAccount);

        TransferRsModel response = TemplateMapper.INSTANCE.buildTransferResponseModel(template);
        log.info(format(TRANSFER_TEMPLATE_CREATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public DebtRsModel createTemplate(DebtRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);

        Template template = templateBuilder.buildTemplate(requestBody, type, user, account);

        DebtRsModel response = TemplateMapper.INSTANCE.buildDebtResponseModel(template);
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

        InOutRsModel response = TemplateMapper.INSTANCE.buildInOutResponseModel(updatedTemplate);
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

        TransferRsModel response = TemplateMapper.INSTANCE.buildTransferResponseModel(updatedTemplate);
        log.info(format(TRANSFER_TEMPLATE_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public DebtRsModel updateTemplate(UpdateDebtRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Template transaction = templateByIdAndUser(requestBody.getTransactionId(), user);

        Template updatedTemplate = updateTemplateValues(requestBody, account, transaction);

        DebtRsModel response = TemplateMapper.INSTANCE.buildDebtResponseModel(updatedTemplate);
        log.info(format(DEBT_TEMPLATE_UPDATED_MSG, user.getUsername(), response));
        return response;
    }

    @Override
    public List<TransactionRsModel> getAllTemplatesByUser(String username) {
        List<TransactionRsModel> response =
                templateRepo.allByUser(userService.findByUsername(username))
                        .stream()
                        .map(TemplateMapper.INSTANCE::buildGenericResponseModel)
                        .collect(Collectors.toList());

        log.info(String.format(ALL_TEMPLATES_MSG, username, response));
        return response;
    }

    @Transactional
    @Override
    public List<TransactionRsModel> deleteTemplateById(String username, List<String> templateIds) {
        User user = userService.findByUsername(username);
        List<Template> deletedTemplates = templateRepo.deleteByIdList(user, templateIds);

        log.info(String.format(DELETED_TEMPLATES_MSG, username, deletedTemplates));
        return deletedTemplates.stream()
                .map(templateBuilder::buildGenericResponseModel)
                .collect(Collectors.toList());
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

    private Template templateByIdAndUser(String templateId, User user) {
        return templateRepo.byIdAndUser(templateId, user)
                .orElseThrow(() ->
                        new TemplateNotFoundException(
                                format(UNAUTHORIZED_TEMPLATE_MSG, user.getUsername(), templateId)));
    }

}
