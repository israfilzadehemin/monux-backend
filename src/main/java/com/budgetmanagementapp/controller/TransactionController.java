package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.Constant.ACCOUNT_ALL;
import static com.budgetmanagementapp.utility.Constant.SORT_BY_DATETIME;
import static com.budgetmanagementapp.utility.Constant.SORT_DIR_DESC;
import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.DeleteTransactionRqModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.transaction.TransactionDateRqModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.utility.TransactionType;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Transaction")
public class TransactionController {

    private final TransactionService transactionService;

    private static final String REQUEST_PARAM_ACCOUNT_ID = "account-id";
    private static final String REQUEST_PARAM_TRANSACTION_COUNT = "transaction-count";

    @ApiOperation("Create income transaction")
    @PostMapping(TRANSACTION_CREATE_INCOME_URL)
    public ResponseEntity<?> createIncomeTransaction(@RequestBody @Valid InOutRqModel requestBody,
                                                     Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_INCOME_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.INCOME,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @ApiOperation("Create outgoing transaction")
    @PostMapping(TRANSACTION_CREATE_OUTGOING_URL)
    public ResponseEntity<?> createOutgoingTransaction(@RequestBody @Valid InOutRqModel requestBody,
                                                       Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_OUTGOING_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.OUTGOING,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @ApiOperation("Create transfer transaction")
    @PostMapping(TRANSACTION_CREATE_TRANSFER_URL)
    public ResponseEntity<?> createTransferTransaction(@RequestBody @Valid TransferRqModel requestBody,
                                                       Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_TRANSFER_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.TRANSFER,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @ApiOperation("Create debt in transaction")
    @PostMapping(TRANSACTION_CREATE_DEBT_IN_URL)
    public ResponseEntity<?> createDebtInTransaction(@RequestBody @Valid DebtRqModel requestBody,
                                                     Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_DEBT_IN_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.DEBT_IN,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @ApiOperation("Create debt out transaction")
    @PostMapping(TRANSACTION_CREATE_DEBT_OUT_URL)
    public ResponseEntity<?> createDebtOutTransaction(@RequestBody @Valid DebtRqModel requestBody,
                                                      Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_DEBT_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(transactionService.createTransaction(
                                requestBody,
                                TransactionType.DEBT_OUT,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @ApiOperation("Delete transactions")
    @PostMapping(TRANSACTION_DELETE_TRANSACTIONS_URL)
    public ResponseEntity<?> deleteTransactions(@RequestBody @Valid DeleteTransactionRqModel requestBody,
                                                Authentication auth) {
        log.info(format(REQUEST_MSG, TRANSACTION_DELETE_TRANSACTIONS_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.deleteTransactionById(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                requestBody.getTransactionIds()))
                        .build());

    }

    @ApiOperation("Get all transactions")
    @GetMapping(TRANSACTION_GET_ALL_TRANSACTIONS_URL)
    public ResponseEntity<?> getAllTransactions(Authentication auth,
                                                @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID)
                                                        Optional<String> accountId) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_ALL_TRANSACTIONS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.getAllTransactionsByUserAndAccount(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                accountId.orElse(ACCOUNT_ALL)))
                        .build());
    }

    @ApiOperation("Get last transactions by months")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL)
    public ResponseEntity<?> getLastTransactionsForMonths(Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.getLastTransactionsByUserAndDateTimeForMonths(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                LocalDateTime.now()))
                        .build());
    }

    @ApiOperation("Get last transactions by weeks")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL)
    public ResponseEntity<?> getLastTransactionsForWeeks(Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.getLastTransactionsByUserAndDateTimeForWeeks(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                LocalDateTime.now()))
                        .build());
    }

    @ApiOperation("Get transactions between time period by category")
    @PostMapping(TRANSACTION_TRANSACTIONS_BETWEEN_TIME_URL)
    public ResponseEntity<?> transactionsBetweenTimeByCategory(@RequestBody @Valid TransactionDateRqModel requestBody,
                                                               Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.transactionsBetweenTimeByCategory(((UserDetails) auth.getPrincipal()).getUsername(),
                                requestBody.getDateTimeFrom(), requestBody.getDateTimeTo()))
                        .build());
    }

    @ApiOperation("Get last transactions with pagination")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_URL)
    public ResponseEntity<?> getLastTransactions(Authentication auth,
                                                 @RequestParam(name = REQUEST_PARAM_TRANSACTION_COUNT)
                                                         Optional<Integer> transactionCount,
                                                 @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID)
                                                         Optional<String> accountId,
                                                 Optional<String> sortBy, Optional<String> sortDirection) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.getLastTransactionsByUserAndAccount(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                accountId.orElse(ACCOUNT_ALL),
                                1,
                                transactionCount.orElse(1),
                                sortBy.orElse(SORT_BY_DATETIME),
                                sortDirection.orElse(SORT_DIR_DESC)))
                        .build());
    }

    @ApiOperation("Update income and outgoing transactions")
    @PostMapping(TRANSACTION_UPDATE_IN_OUT_URL)
    public ResponseEntity<?> updateInOutTransaction(@RequestBody @Valid UpdateInOutRqModel requestBody,
                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_IN_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @ApiOperation("Update transfer transaction")
    @PostMapping(TRANSACTION_UPDATE_TRANSFER_URL)
    public ResponseEntity<?> updateTransferTransaction(@RequestBody @Valid UpdateTransferRqModel requestBody,
                                                       Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_TRANSFER_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @ApiOperation("Update debt in or out transaction")
    @PostMapping(TRANSACTION_UPDATE_DEBT_URL)
    public ResponseEntity<?> updateDebtTransaction(@RequestBody @Valid UpdateDebtRqModel requestBody,
                                                   Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_DEBT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

}
