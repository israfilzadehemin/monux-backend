package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.Constant.ACCOUNT_ALL;
import static com.budgetmanagementapp.utility.Constant.SORT_BY_DATETIME;
import static com.budgetmanagementapp.utility.Constant.SORT_DIR_DESC;
import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.model.transaction.*;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
import com.budgetmanagementapp.service.TransactionService;
import com.budgetmanagementapp.utility.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    public ResponseEntity<ResponseModel<TransactionRsModel>> createIncomeTransaction(@RequestBody @Valid InOutRqModel requestBody,
                                                                                     Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_INCOME_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.createTransaction(
                                requestBody,
                                TransactionType.INCOME,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create outgoing transaction")
    @PostMapping(TRANSACTION_CREATE_OUTGOING_URL)
    public ResponseEntity<?> createOutgoingTransaction(@RequestBody @Valid InOutRqModel requestBody,
                                                       Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_OUTGOING_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.createTransaction(
                                requestBody,
                                TransactionType.OUTGOING,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create transfer transaction")
    @PostMapping(TRANSACTION_CREATE_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> createTransferTransaction(@RequestBody @Valid TransferRqModel requestBody,
                                                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_TRANSFER_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.createTransaction(
                                requestBody,
                                TransactionType.TRANSFER,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create debt in transaction")
    @PostMapping(TRANSACTION_CREATE_DEBT_IN_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtInTransaction(@RequestBody @Valid DebtRqModel requestBody,
                                                     Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_DEBT_IN_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(transactionService.createTransaction(
                                requestBody,
                                TransactionType.DEBT_IN,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create debt out transaction")
    @PostMapping(TRANSACTION_CREATE_DEBT_OUT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtOutTransaction(@RequestBody @Valid DebtRqModel requestBody,
                                                      Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_CREATE_DEBT_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(transactionService.createTransaction(
                                requestBody,
                                TransactionType.DEBT_OUT,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Delete transactions")
    @PostMapping(TRANSACTION_DELETE_TRANSACTIONS_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> deleteTransactions(@RequestBody @Valid DeleteTransactionRqModel requestBody,
                                                                                      Authentication auth) {
        log.info(format(REQUEST_MSG, TRANSACTION_DELETE_TRANSACTIONS_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(transactionService.deleteTransactionById(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                requestBody.getTransactionIds()), HttpStatus.OK));
    }

    @ApiOperation("Get all transactions")
    @GetMapping(TRANSACTION_GET_ALL_TRANSACTIONS_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getAllTransactions(Authentication auth,
                                                @ApiParam(
                                                        name = REQUEST_PARAM_ACCOUNT_ID,
                                                        type = "string",
                                                        example = "500de72f-7e0d-4fa9-bcca-4069629c2648",
                                                        required = true)
                                                @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID)
                                                        Optional<String> accountId) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_ALL_TRANSACTIONS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.getAllTransactionsByUserAndAccount(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                accountId.orElse(ACCOUNT_ALL)), HttpStatus.OK));
    }

    @ApiOperation("Get last transactions by months")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL)
    public ResponseEntity<ResponseModel<AmountListRsModel>> getLastTransactionsForMonths(Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.getLastTransactionsByUserAndDateTimeForMonths(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                LocalDateTime.now()), HttpStatus.OK));
    }

    @ApiOperation("Get last transactions by weeks")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL)
    public ResponseEntity<ResponseModel<AmountListRsModel>> getLastTransactionsForWeeks(Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.getLastTransactionsByUserAndDateTimeForWeeks(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                LocalDateTime.now()), HttpStatus.OK));
    }

    @ApiOperation("Get transactions between time period by category")
    @PostMapping(TRANSACTION_TRANSACTIONS_BETWEEN_TIME_URL)
    public ResponseEntity<ResponseModel<CategoryAmountListRsModel>> transactionsBetweenTimeByCategory(@RequestBody @Valid TransactionDateRqModel requestBody,
                                                               Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.transactionsBetweenTimeByCategory(((UserDetails) auth.getPrincipal()).getUsername(),
                                requestBody.getDateTimeFrom(), requestBody.getDateTimeTo()), HttpStatus.OK));
    }

    @ApiOperation("Get last transactions with pagination")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getLastTransactions(Authentication auth,
                                                 @RequestParam(name = REQUEST_PARAM_TRANSACTION_COUNT)
                                                         Optional<Integer> transactionCount,
                                                 @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID)
                                                         Optional<String> accountId,
                                                 Optional<String> sortBy, Optional<String> sortDirection) {

        log.info(format(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(transactionService.getLastTransactionsByUserAndAccount(
                                ((UserDetails) auth.getPrincipal()).getUsername(),
                                accountId.orElse(ACCOUNT_ALL),
                                1,
                                transactionCount.orElse(1),
                                sortBy.orElse(SORT_BY_DATETIME),
                                sortDirection.orElse(SORT_DIR_DESC)), HttpStatus.OK));
    }

    @ApiOperation("Update income and outgoing transactions")
    @PostMapping(TRANSACTION_UPDATE_IN_OUT_URL)
    public ResponseEntity<ResponseModel<InOutRsModel>> updateInOutTransaction(@RequestBody @Valid UpdateInOutRqModel requestBody,
                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_IN_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

    @ApiOperation("Update transfer transaction")
    @PostMapping(TRANSACTION_UPDATE_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> updateTransferTransaction(@RequestBody @Valid UpdateTransferRqModel requestBody,
                                                       Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_TRANSFER_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

    @ApiOperation("Update debt in or out transaction")
    @PostMapping(TRANSACTION_UPDATE_DEBT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> updateDebtTransaction(@RequestBody @Valid UpdateDebtRqModel requestBody,
                                                   Authentication auth) {

        log.info(format(REQUEST_MSG, TRANSACTION_UPDATE_DEBT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(transactionService.updateTransaction(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

}
