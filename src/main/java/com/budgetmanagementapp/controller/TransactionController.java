package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transaction.*;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
import com.budgetmanagementapp.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.budgetmanagementapp.utility.Constant.*;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Transaction")
public class TransactionController {

    private static final String REQUEST_PARAM_ACCOUNT_ID = "account-id";
    private static final String REQUEST_PARAM_TRANSACTION_COUNT = "transaction-count";
    private final TransactionService transactionService;

    @ApiOperation("Create income transaction")
    @PostMapping(TRANSACTION_CREATE_INCOME_URL)
    public ResponseEntity<ResponseModel<TransactionRsModel>> createIncomeTransaction(
            @RequestBody @Valid InOutRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_CREATE_INCOME_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, INCOME, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTION_CREATE_INCOME_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create outgoing transaction")
    @PostMapping(TRANSACTION_CREATE_OUTGOING_URL)
    public ResponseEntity<?> createOutgoingTransaction(
            @RequestBody @Valid InOutRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_CREATE_OUTGOING_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, OUTGOING, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTION_CREATE_OUTGOING_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create transfer transaction")
    @PostMapping(TRANSACTION_CREATE_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> createTransferTransaction(
            @RequestBody @Valid TransferRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_CREATE_TRANSFER_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, TRANSFER, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTION_CREATE_TRANSFER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create debt in transaction")
    @PostMapping(TRANSACTION_CREATE_DEBT_IN_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtInTransaction(
            @RequestBody @Valid DebtRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_CREATE_DEBT_IN_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, DEBT_IN, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTION_CREATE_DEBT_IN_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create debt out transaction")
    @PostMapping(TRANSACTION_CREATE_DEBT_OUT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtOutTransaction(
            @RequestBody @Valid DebtRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_CREATE_DEBT_OUT_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, DEBT_OUT, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTION_CREATE_DEBT_OUT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete transactions")
    @PostMapping(TRANSACTION_DELETE_TRANSACTIONS_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> deleteTransactions(
            @RequestBody @Valid DeleteTransactionRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_DELETE_TRANSACTIONS_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.deleteTransactionById(
                        ((UserDetails) auth.getPrincipal()).getUsername(), requestBody.getTransactionIds()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTION_DELETE_TRANSACTIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all transactions")
    @GetMapping(TRANSACTION_GET_ALL_TRANSACTIONS_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getAllTransactions(
            Authentication auth,
            @ApiParam(name = REQUEST_PARAM_ACCOUNT_ID, type = "string", example = "500de72f-7e...", required = true)
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) Optional<String> accountId) {

        log.info(REQUEST_MSG, TRANSACTION_GET_ALL_TRANSACTIONS_URL, accountId);
        var response = ResponseModel.of(
                transactionService.getAllTransactionsByUserAndAccount(
                        ((UserDetails) auth.getPrincipal()).getUsername(), accountId.orElse(ACCOUNT_ALL)),
                OK);

        log.info(RESPONSE_MSG, TRANSACTION_GET_ALL_TRANSACTIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get last transactions by months")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL)
    public ResponseEntity<ResponseModel<AmountListRsModel>> getLastTransactionsForMonths(Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                transactionService.getLastTransactionsByUserAndDateTimeForMonths(
                        ((UserDetails) auth.getPrincipal()).getUsername(), LocalDateTime.now()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get last transactions with pagination")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getLastTransactions(
            Authentication auth,
            @RequestParam(name = REQUEST_PARAM_TRANSACTION_COUNT) Optional<Integer> transactionCount,
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) Optional<String> accountId,
            Optional<String> sortBy,
            Optional<String> sortDirection) {

        log.info(REQUEST_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_URL, accountId);
        var response = ResponseModel.of(
                transactionService.getLastTransactionsByUserAndAccount(
                        ((UserDetails) auth.getPrincipal()).getUsername(),
                        accountId.orElse(ACCOUNT_ALL),
                        1,
                        transactionCount.orElse(1),
                        sortBy.orElse(SORT_BY_DATETIME),
                        sortDirection.orElse(SORT_DIR_DESC)), OK);

        log.info(RESPONSE_MSG, TRANSACTION_GET_LAST_TRANSACTIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update income and outgoing transactions")
    @PostMapping(TRANSACTION_UPDATE_IN_OUT_URL)
    public ResponseEntity<ResponseModel<InOutRsModel>> updateInOutTransaction(
            @RequestBody @Valid UpdateInOutRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_UPDATE_IN_OUT_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.updateTransaction(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTION_UPDATE_IN_OUT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update transfer transaction")
    @PostMapping(TRANSACTION_UPDATE_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> updateTransferTransaction(
            @RequestBody @Valid UpdateTransferRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_UPDATE_TRANSFER_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.updateTransaction(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTION_UPDATE_TRANSFER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update debt in or out transaction")
    @PostMapping(TRANSACTION_UPDATE_DEBT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> updateDebtTransaction(
            @RequestBody @Valid UpdateDebtRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTION_UPDATE_DEBT_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.updateTransaction(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTION_UPDATE_DEBT_URL, response);
        return ResponseEntity.ok(response);
    }

}
