package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.DebtRqModel;
import com.budgetmanagementapp.model.DebtRsModel;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.model.TransactionResponseModel;
import com.budgetmanagementapp.model.TransferRequestModel;
import com.budgetmanagementapp.model.TransferResponseModel;
import com.budgetmanagementapp.model.UpdateDebtRqModel;
import com.budgetmanagementapp.model.UpdateInOutRequestModel;
import com.budgetmanagementapp.model.UpdateTransferRequestModel;
import com.budgetmanagementapp.utility.TransactionType;
import java.util.List;

public interface TransactionService {
    TransactionResponseModel createTransaction(InOutRequestModel requestBody,
                                               TransactionType transactionType,
                                               String username);


    TransferResponseModel createTransaction(TransferRequestModel requestBody,
                                            TransactionType transactionType,
                                            String username);

    DebtRsModel createTransaction(DebtRqModel requestBody,
                                  TransactionType transactionType,
                                  String username);

    InOutResponseModel updateTransaction(UpdateInOutRequestModel requestBody, String username);

    TransferResponseModel updateTransaction(UpdateTransferRequestModel requestBody, String username);

    DebtRsModel updateTransaction(UpdateDebtRqModel requestBody, String username);

    List<TransactionResponseModel> getAllTransactionsByUser(String username);
}
