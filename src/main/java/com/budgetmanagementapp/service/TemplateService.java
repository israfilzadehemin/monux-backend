package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.transaction.*;
import com.budgetmanagementapp.model.transaction.TransferRqModel;
import com.budgetmanagementapp.model.transaction.TransferRsModel;
import com.budgetmanagementapp.utility.TransactionType;

import java.util.List;

public interface TemplateService {
    TransactionRsModel createTemplate(InOutRqModel requestBody,
                                      TransactionType transactionType,
                                      String username);


    TransferRsModel createTemplate(TransferRqModel requestBody,
                                   TransactionType transactionType,
                                   String username);

    DebtRsModel createTemplate(DebtRqModel requestBody,
                               TransactionType transactionType,
                               String username);

    InOutRsModel updateTemplate(InOutRqModel requestBody, String templateId, String username);

    TransferRsModel updateTemplate(TransferRqModel requestBody, String templateId, String username);

    DebtRsModel updateTemplate(DebtRqModel requestBody, String templateId, String username);

    List<TransactionRsModel> getAllTemplatesByUser(String username);

    List<TransactionRsModel> deleteTemplateById(String username, List<String> templateIds);
}
