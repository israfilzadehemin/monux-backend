package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.mapper.TemplateMapper.TEMPLATE_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_TEMPLATES_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TEMPLATE_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TEMPLATE_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DELETED_TEMPLATES_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TEMPLATE_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TEMPLATE_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TEMPLATE_BY_ID_USER_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TEMPLATE_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TEMPLATE_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TO_SELF_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_TEMPLATE_MSG;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.valueOf;
import static java.lang.String.format;

import com.budgetmanagementapp.builder.TemplateBuilder;
import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Template;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.TemplateNotFoundException;
import com.budgetmanagementapp.exception.TransferToSelfException;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
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
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
    public InOutRsModel updateTemplate(UpdateInOutRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Template template = templateByIdAndUser(requestBody.getTransactionId(), user);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category =
                categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), valueOf(template.getType()), user);
        List<Label> labels = labelService.allByIdsAndTypeAndUser(requestBody.getLabelIds(), template.getType(), user);

        Template updatedTemplate = updateTemplateValues(requestBody, template, account, category, labels);

        InOutRsModel inOutRsModel = TEMPLATE_MAPPER_INSTANCE.buildInOutResponseModel(updatedTemplate);

        log.info(format(IN_OUT_TEMPLATE_UPDATED_MSG, user.getUsername(), inOutRsModel));
        return inOutRsModel;
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

        TransferRsModel transferRsModel = TEMPLATE_MAPPER_INSTANCE.buildTransferResponseModel(updatedTemplate);

        log.info(TRANSFER_TEMPLATE_UPDATED_MSG, user.getUsername(), transferRsModel);
        return transferRsModel;
    }

    @Override
    public DebtRsModel updateTemplate(UpdateDebtRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Template transaction = templateByIdAndUser(requestBody.getTransactionId(), user);

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

    private Template updateTemplateValues(UpdateInOutRqModel requestBody, Template template,
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

    private Template updateTemplateValues(UpdateDebtRqModel requestBody, Account account, Template template) {
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
                        new TemplateNotFoundException(
                                format(UNAUTHORIZED_TEMPLATE_MSG, user.getUsername(), templateId)));

        log.info(TEMPLATE_BY_ID_USER_MSG, templateId, user, template);
        return template;
    }

}
