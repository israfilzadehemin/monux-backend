package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_CREATE_DEBT_IN_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_CREATE_DEBT_OUT_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_CREATE_INCOME_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_CREATE_OUTCOME_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_CREATE_TRANSFER_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_GET_ALL_TRANSACTIONS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_UPDATE_DEBT_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_UPDATE_IN_OUT_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_UPDATE_TRANSFER_TRANSACTION_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.DebtRequestModel;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.TransferRequestModel;
import com.budgetmanagementapp.model.UpdateDebtRequestModel;
import com.budgetmanagementapp.model.UpdateInOutRequestModel;
import com.budgetmanagementapp.model.UpdateTransferRequestModel;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.utility.TransactionType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(TRANSACTION_CREATE_INCOME_TRANSACTION_URL)
    public ResponseEntity<?> createIncomeTransaction(@RequestBody InOutRequestModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_INCOME_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.INCOME,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TRANSACTION_CREATE_OUTCOME_TRANSACTION_URL)
    public ResponseEntity<?> createOutcomeTransaction(@RequestBody InOutRequestModel requestBody,
                                                      Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_OUTCOME_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.OUTCOME,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TRANSACTION_CREATE_TRANSFER_TRANSACTION_URL)
    public ResponseEntity<?> createTransferTransaction(
            @RequestBody TransferRequestModel requestBody,
            Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_TRANSFER_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.TRANSFER,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TRANSACTION_CREATE_DEBT_IN_TRANSACTION_URL)
    public ResponseEntity<?> createDebtInTransaction(
            @RequestBody DebtRequestModel requestBody,
            Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_DEBT_IN_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.DEBT_IN,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TRANSACTION_CREATE_DEBT_OUT_TRANSACTION_URL)
    public ResponseEntity<?> createDebtOutTransaction(
            @RequestBody DebtRequestModel requestBody,
            Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_DEBT_OUT_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.DEBT_OUT,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @GetMapping(TRANSACTION_GET_ALL_TRANSACTIONS_URL)
    public ResponseEntity<?> getAllTransactions(Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_ALL_TRANSACTIONS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.getAllTransactionsByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }


    @PostMapping(TRANSACTION_UPDATE_IN_OUT_TRANSACTION_URL)
    public ResponseEntity<?> updateInOutTransaction(@RequestBody UpdateInOutRequestModel requestBody,
                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_IN_OUT_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TRANSACTION_UPDATE_TRANSFER_TRANSACTION_URL)
    public ResponseEntity<?> updateTransferTransaction(@RequestBody UpdateTransferRequestModel requestBody,
                                                       Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_TRANSFER_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TRANSACTION_UPDATE_DEBT_TRANSACTION_URL)
    public ResponseEntity<?> updateDebtTransaction(@RequestBody UpdateDebtRequestModel requestBody,
                                                   Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_DEBT_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

}
