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

public interface TransactionService {
    TransactionRsModel createTransaction(InOutRqModel requestBody,
                                         TransactionType transactionType,
                                         String username);


    TransferRsModel createTransaction(TransferRqModel requestBody,
                                      TransactionType transactionType,
                                      String username);

    DebtRsModel createTransaction(DebtRqModel requestBody,
                                  TransactionType transactionType,
                                  String username);

    InOutRsModel updateTransaction(UpdateInOutRqModel requestBody, String username);

    TransferRsModel updateTransaction(UpdateTransferRqModel requestBody, String username);

    DebtRsModel updateTransaction(UpdateDebtRqModel requestBody, String username);

    List<TransactionRsModel> getAllTransactionsByUserAndAccount(String username, String accountId);

    List<TransactionRsModel> getLastTransactionsByUserAndAccount(String username, String accountId, int pageCount, int size, String sortField, String sortDir);

    List<TransactionRsModel> deleteTransactionById(String username, List<String> transactionIds);
}
