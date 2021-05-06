package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TEMPLATE_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DEBT_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.IN_OUT_TEMPLATE_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TEMPLATE_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TO_SELF_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TRANSACTION_CREATED_MSG;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.Template;
import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.entity.User;
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
import com.budgetmanagementapp.service.TagService;
import com.budgetmanagementapp.service.TemplateService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomFormatter;
import com.budgetmanagementapp.utility.TransactionType;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
    private final TagService tagService;
    private final TemplateRepository templateRepo;


    @Override
    public TransactionRsModel createTemplate(InOutRqModel requestBody, TransactionType type, String username) {
        User user = userService.findByUsername(username);
        Account account = accountService.byIdAndUser(requestBody.getAccountId(), user);
        Category category = categoryService.byIdAndTypeAndUser(requestBody.getCategoryId(), type, user);
        List<Tag> tags = tagService.allByIdsAndTypeAndUser(requestBody.getTagIds(), type.name(), user);

        Template template = buildTemplate(requestBody, user, account, category, tags, type);

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
        return null;
    }

    @Override
    public TransferRsModel updateTemplate(UpdateTransferRqModel requestBody, String username) {
        return null;
    }

    @Override
    public DebtRsModel updateTemplate(UpdateDebtRqModel requestBody, String username) {
        return null;
    }

    @Override
    public List<TransactionRsModel> getAllTemplatesByUser(String username) {
        return null;
    }

    private Template buildTemplate(InOutRqModel requestBody, User user,
                                   Account account, Category category,
                                   List<Tag> tags, TransactionType type) {
        Template template = Template.builder()
                .templateId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .category(category)
                .tags(tags)
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
                .tagIds(template.getTags().stream().map(Tag::getTagId).collect(Collectors.toList()))
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

}
