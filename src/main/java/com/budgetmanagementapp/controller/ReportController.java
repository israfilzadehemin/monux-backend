package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTIONS_BY_CATEGORY_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTIONS_BY_MONTHS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTIONS_BY_WEEKS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTIONS_URL;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.transaction.AmountListRsModel;
import com.budgetmanagementapp.model.transaction.CategoryAmountListRsModel;
import com.budgetmanagementapp.model.transaction.TransactionDateRqModel;
import com.budgetmanagementapp.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Report")
@RequestMapping(TRANSACTIONS_URL)
public class ReportController {

    private final TransactionService transactionService;

    @ApiOperation("Get last transactions by months")
    @GetMapping(TRANSACTIONS_BY_MONTHS_URL)
    public ResponseEntity<ResponseModel<AmountListRsModel>> getLastTransactionsForMonths(@ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTIONS_BY_MONTHS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                transactionService.getTransactionReportInMonths(
                        ((UserDetails) auth.getPrincipal()).getUsername(), LocalDateTime.now()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTIONS_BY_MONTHS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get last transactions by weeks")
    @GetMapping(TRANSACTIONS_BY_WEEKS_URL)
    public ResponseEntity<ResponseModel<AmountListRsModel>> getLastTransactionsForWeeks(@ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTIONS_BY_WEEKS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                transactionService.getTransactionReportInWeeks(
                        ((UserDetails) auth.getPrincipal()).getUsername(), LocalDateTime.now()), OK);


        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTIONS_BY_WEEKS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get transactions between time period by category")
    @PostMapping(TRANSACTIONS_BY_CATEGORY_URL)
    public ResponseEntity<ResponseModel<CategoryAmountListRsModel>> transactionsBetweenTimeByCategory(
            @RequestBody @Valid TransactionDateRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTIONS_BY_CATEGORY_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.transactionsBetweenTimeByCategory(
                        ((UserDetails) auth.getPrincipal()).getUsername(),
                        requestBody.getDateTimeFrom(),
                        requestBody.getDateTimeTo()), OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTIONS_BY_CATEGORY_URL, response);
        return ResponseEntity.ok(response);
    }
}
