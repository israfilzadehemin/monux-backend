package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.account.DebtRqModel;
import com.budgetmanagementapp.model.account.DebtRsModel;
import com.budgetmanagementapp.model.account.InOutRqModel;
import com.budgetmanagementapp.model.account.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
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
