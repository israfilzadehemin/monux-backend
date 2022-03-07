package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.Constant.ACCOUNT_ALL;
import static com.budgetmanagementapp.utility.Constant.SORT_BY_DATETIME;
import static com.budgetmanagementapp.utility.Constant.SORT_DIR_DESC;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_OUT;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.OUTGOING;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTIONS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_BY_ID_DEBT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_BY_ID_IN_OUT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_BY_ID_TRANSFER_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_DEBT_IN_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_DEBT_OUT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_GET_LAST_TRANSACTIONS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_INCOME_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_OUTGOING_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TRANSACTION_TRANSFER_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.transaction.DebtRqModel;
import com.budgetmanagementapp.model.transaction.DebtRsModel;
import com.budgetmanagementapp.model.transaction.DeleteTransactionRqModel;
import com.budgetmanagementapp.model.transaction.InOutRqModel;
import com.budgetmanagementapp.model.transaction.InOutRsModel;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import com.budgetmanagementapp.model.transaction.TransferRqModel;
import com.budgetmanagementapp.model.transaction.TransferRsModel;
import com.budgetmanagementapp.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Transaction")
@RequestMapping(TRANSACTIONS_URL)
public class TransactionController {

    private static final String REQUEST_PARAM_ACCOUNT_ID = "account-id";
    private static final String REQUEST_PARAM_TRANSACTION_COUNT = "transaction-count";
    private final TransactionService transactionService;

    @ApiOperation("Create income transaction")
    @PostMapping(TRANSACTION_INCOME_URL)
    public ResponseEntity<ResponseModel<TransactionRsModel>> createIncomeTransaction(
            @RequestBody @Valid InOutRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_INCOME_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, INCOME, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_INCOME_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create outgoing transaction")
    @PostMapping(TRANSACTION_OUTGOING_URL)
    public ResponseEntity<?> createOutgoingTransaction(
            @RequestBody @Valid InOutRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_OUTGOING_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, OUTGOING, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_OUTGOING_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create transfer transaction")
    @PostMapping(TRANSACTION_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> createTransferTransaction(
            @RequestBody @Valid TransferRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_TRANSFER_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, TRANSFER, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_TRANSFER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create debt in transaction")
    @PostMapping(TRANSACTION_DEBT_IN_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtInTransaction(
            @RequestBody @Valid DebtRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_DEBT_IN_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, DEBT_IN, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_DEBT_IN_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create debt out transaction")
    @PostMapping(TRANSACTION_DEBT_OUT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtOutTransaction(
            @RequestBody @Valid DebtRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_DEBT_OUT_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.createTransaction(
                        requestBody, DEBT_OUT, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_DEBT_OUT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete transactions")
    @DeleteMapping
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> deleteTransactions(
            @RequestBody @Valid DeleteTransactionRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.deleteTransactionById(
                        ((UserDetails) auth.getPrincipal()).getUsername(), requestBody.getTransactionIds()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all transactions")
    @GetMapping
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getAllTransactions(
            @ApiIgnore Authentication auth,
            @ApiParam(name = REQUEST_PARAM_ACCOUNT_ID, type = "string", example = "500de72f-7e...")
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) Optional<String> accountId) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL, accountId);
        var response = ResponseModel.of(
                transactionService.getAllTransactionsByUserAndAccount(
                        ((UserDetails) auth.getPrincipal()).getUsername(), accountId.orElse(ACCOUNT_ALL)),
                OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get last transactions with pagination")
    @GetMapping(TRANSACTION_GET_LAST_TRANSACTIONS_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getLastTransactions(
            @ApiIgnore Authentication auth,
            @RequestParam(name = REQUEST_PARAM_TRANSACTION_COUNT) Optional<Integer> transactionCount,
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) Optional<String> accountId,
            Optional<String> sortBy,
            Optional<String> sortDirection) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_GET_LAST_TRANSACTIONS_URL, accountId);
        var response = ResponseModel.of(
                transactionService.getLastTransactionsByUserAndAccount(
                        ((UserDetails) auth.getPrincipal()).getUsername(),
                        accountId.orElse(ACCOUNT_ALL),
                        1,
                        transactionCount.orElse(1),
                        sortBy.orElse(SORT_BY_DATETIME),
                        sortDirection.orElse(SORT_DIR_DESC)), OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_GET_LAST_TRANSACTIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update income and outgoing transactions")
    @PutMapping(TRANSACTION_BY_ID_IN_OUT_URL)
    public ResponseEntity<ResponseModel<InOutRsModel>> updateInOutTransaction(
            @RequestBody @Valid InOutRqModel requestBody,
            @PathVariable("id") String transactionId, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_BY_ID_IN_OUT_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.updateTransaction(requestBody, transactionId, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_BY_ID_IN_OUT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update transfer transaction")
    @PutMapping(TRANSACTION_BY_ID_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> updateTransferTransaction(
            @RequestBody @Valid TransferRqModel requestBody,
            @PathVariable("id") String transactionId, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_BY_ID_TRANSFER_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.updateTransaction(requestBody, transactionId, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_BY_ID_TRANSFER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update debt in or out transaction")
    @PutMapping(TRANSACTION_BY_ID_DEBT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> updateDebtTransaction(
            @RequestBody @Valid DebtRqModel requestBody,
            @PathVariable("id") String transactionId, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TRANSACTIONS_URL + TRANSACTION_BY_ID_DEBT_URL, requestBody);
        var response = ResponseModel.of(
                transactionService.updateTransaction(requestBody, transactionId, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TRANSACTIONS_URL + TRANSACTION_BY_ID_DEBT_URL, response);
        return ResponseEntity.ok(response);
    }

}
