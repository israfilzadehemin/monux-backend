package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.model.TransferRequestModel;
import com.budgetmanagementapp.model.TransferResponseModel;
import com.budgetmanagementapp.utility.TransactionType;

public interface TransactionService {
    InOutResponseModel createInOutTransaction(InOutRequestModel requestBody,
                                              TransactionType transactionType,
                                              String username);

    TransferResponseModel createTransferTransaction(TransferRequestModel requestBody,
                                                    TransactionType transactionType,
                                                    String username);
}
