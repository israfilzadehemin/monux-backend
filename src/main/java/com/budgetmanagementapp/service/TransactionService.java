package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.DebtRequestModel;
import com.budgetmanagementapp.model.DebtResponseModel;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.model.TransactionResponseModel;
import com.budgetmanagementapp.model.TransferRequestModel;
import com.budgetmanagementapp.model.TransferResponseModel;
import com.budgetmanagementapp.model.UpdateDebtRequestModel;
import com.budgetmanagementapp.model.UpdateTransactionRequestModel;
import com.budgetmanagementapp.model.UpdateTransferRequestModel;
import com.budgetmanagementapp.utility.TransactionType;
import java.util.List;

public interface TransactionService {
    TransactionResponseModel createInOutTransaction(InOutRequestModel requestBody,
                                                    TransactionType transactionType,
                                                    String username);


    TransferResponseModel createTransferTransaction(TransferRequestModel requestBody,
                                                TransactionType transactionType,
                                                String username);

    DebtResponseModel createDebtTransaction(DebtRequestModel requestBody,
                                                TransactionType transactionType,
                                                String username);

    InOutResponseModel updateTransaction(UpdateTransactionRequestModel requestBody, String username);

    TransferResponseModel updateTransaction(UpdateTransferRequestModel requestBody, String username);

    DebtResponseModel updateTransaction(UpdateDebtRequestModel requestBody, String username);

    List<TransactionResponseModel> getAllTransactionsByUser(String username);
}
