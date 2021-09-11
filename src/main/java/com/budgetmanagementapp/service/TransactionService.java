package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
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
