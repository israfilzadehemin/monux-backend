package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_CREATE_INCOME_TRANSACTION_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_CREATE_OUTCOME_TRANSACTION_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.service.DebtTransactionService;
import com.budgetmanagementapp.service.InOutTransactionService;
import com.budgetmanagementapp.service.TransferTransactionService;
import com.budgetmanagementapp.utility.TransactionType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class TransactionController {

    private final InOutTransactionService inOutTransactionService;
    private final TransferTransactionService transferTransactionService;
    private final DebtTransactionService debtTransactionService;

    @PostMapping(TRANSACTION_CREATE_INCOME_TRANSACTION_URL)
    public ResponseEntity<?> createIncomeTransaction(@RequestBody InOutRequestModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_INCOME_TRANSACTION_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(inOutTransactionService.createInOutTransaction(
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
                        .body(inOutTransactionService.createInOutTransaction(
                                requestBody,
                                TransactionType.OUTCOME,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }
}
