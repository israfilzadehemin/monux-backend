package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.transaction.AmountListRsModel;
import com.budgetmanagementapp.model.transaction.CategoryAmountListRsModel;
import com.budgetmanagementapp.model.transaction.TransactionDateRqModel;
import com.budgetmanagementapp.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_TRANSACTIONS_BETWEEN_TIME_URL;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Report")
public class ReportController {

    private final TransactionService transactionService;

    @ApiOperation("Get last transactions by weeks")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL)
    public ResponseEntity<ResponseModel<AmountListRsModel>> getLastTransactionsForWeeks(Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                transactionService.getLastTransactionsByUserAndDateTimeForWeeks(
                        ((UserDetails) auth.getPrincipal()).getUsername(), LocalDateTime.now()), OK);


        log.info(RESPONSE_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get transactions between time period by category")
    @PostMapping(TRANSACTION_TRANSACTIONS_BETWEEN_TIME_URL)
    public ResponseEntity<ResponseModel<CategoryAmountListRsModel>> transactionsBetweenTimeByCategory(
            @RequestBody @Valid TransactionDateRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.transactionsBetweenTimeByCategory(
                        ((UserDetails) auth.getPrincipal()).getUsername(),
                        requestBody.getDateTimeFrom(),
                        requestBody.getDateTimeTo()), OK);

        log.info(RESPONSE_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, response);
        return ResponseEntity.ok(response);
    }
}
