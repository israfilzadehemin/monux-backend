package com.budgetmanagementapp.service;

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

    InOutRsModel updateTemplate(UpdateInOutRqModel requestBody, String username);

    TransferRsModel updateTemplate(UpdateTransferRqModel requestBody, String username);

    DebtRsModel updateTemplate(UpdateDebtRqModel requestBody, String username);

    List<TransactionRsModel> getAllTemplatesByUser(String username);

    List<TransactionRsModel> deleteTemplateById(String username, List<String> templateIds);
}
