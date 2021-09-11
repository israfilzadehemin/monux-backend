package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.mapper.TransactionMapper;
import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.repository.TransactionRepository;
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
public class TransactionBuilder {

    private final TransactionRepository transactionRepo;

    public Transaction buildTransaction(InOutRqModel requestBody, User user, Account account,
                                        Category category, List<Label> labels, TransactionType type) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .category(category)
                .labels(labels)
                .user(user)
                .build();

        if (type.equals(INCOME)) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }
        transactionRepo.save(transaction);
        return transaction;
    }

    public Transaction buildTransaction(TransferRqModel requestBody, User user,
                                        Account senderAccount,
                                        Account receiverAccount) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .type(TRANSFER.name())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .user(user)
                .build();
        transactionRepo.save(transaction);
        return transaction;
    }

    public Transaction buildTransaction(DebtRqModel requestBody,
                                        TransactionType type,
                                        Account account,
                                        User user) {
        Transaction transaction = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(CustomFormatter.stringToLocalDateTime(requestBody.getDateTime()))
                .amount(requestBody.getAmount())
                .description(requestBody.getDescription())
                .type(type.name())
                .user(user)
                .build();
        if (type.equals(DEBT_IN)) {
            transaction.setReceiverAccount(account);
        } else {
            transaction.setSenderAccount(account);
        }
        transactionRepo.save(transaction);
        return transaction;
    }

    public TransactionRsModel buildGenericResponseModel(Transaction transaction) {
        TransactionRsModel response = TransactionMapper.INSTANCE.buildGenericResponseModel(transaction);

        if (!Objects.isNull(transaction.getSenderAccount())) {
            response.setSenderAccountId(transaction.getSenderAccount().getAccountId());
        }
        if (!Objects.isNull(transaction.getReceiverAccount())) {
            response.setReceiverAccountId(transaction.getReceiverAccount().getAccountId());
        }
        if (!Objects.isNull(transaction.getCategory())) {
            response.setCategoryId(transaction.getCategory().getCategoryId());
        }
        if (!Objects.isNull(transaction.getLabels())) {
            response.setLabelIds(transaction.getLabels().stream().map(Label::getLabelId).collect(Collectors.toList()));
        }
        return response;
    }


}
