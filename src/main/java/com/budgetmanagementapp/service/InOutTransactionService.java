package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.InOutResponseModel;
import com.budgetmanagementapp.utility.TransactionType;

public interface InOutTransactionService {
    InOutResponseModel createInOutTransaction(InOutRequestModel requestBody,
                                              TransactionType transactionType,
                                              String username);
}
