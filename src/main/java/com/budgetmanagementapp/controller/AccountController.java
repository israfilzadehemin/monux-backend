package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.UpdateAccountModel;
import com.budgetmanagementapp.model.account.UpdateBalanceModel;
import com.budgetmanagementapp.service.AccountService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Log4j2
@Validated
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Account")
public class AccountController {
    private final AccountService accountService;

    private static final String REQUEST_PARAM_ACCOUNT_ID = "account-id";

    @ApiOperation("Create account")
    @PostMapping(ACCOUNT_CREATE_URL)
    public ResponseEntity<?> createAccount(@RequestBody AccountRqModel requestBody, Authentication auth) {
        requestBody.setUsername(((UserDetails) auth.getPrincipal()).getUsername());

        log.info(format(REQUEST_MSG, ACCOUNT_CREATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(accountService.createAccount(requestBody, false))
                        .build());
    }

    @ApiOperation("Update account")
    @PostMapping(ACCOUNT_UPDATE_URL)
    public ResponseEntity<?> updateAccount(@RequestBody @Valid UpdateAccountModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_UPDATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService
                                .updateAccount(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @ApiOperation("Update balance of account")
    @PostMapping(ACCOUNT_UPDATE_BALANCE_URL)
    public ResponseEntity<?> updateBalance(@RequestBody @Valid UpdateBalanceModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_UPDATE_BALANCE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService
                                .updateBalance(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @ApiOperation("Get all accounts")
    @GetMapping(ACCOUNT_GET_ALL_ACCOUNTS_URL)
    public ResponseEntity<?> getAllAccounts(Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_GET_ALL_ACCOUNTS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService.getAllAccountsByUser(((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @ApiOperation("Get all account types")
    @GetMapping(ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL)
    public ResponseEntity<?> getAllAccountTypes() {

        log.info(format(REQUEST_MSG, ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService.getAllAccountTypes())
                        .build());
    }

    @ApiOperation("Get all currencies")
    @GetMapping(ACCOUNT_GET_ALL_CURRENCIES_URL)
    public ResponseEntity<?> getAllCurrencies() {

        log.info(format(REQUEST_MSG, ACCOUNT_GET_ALL_CURRENCIES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService.getAllCurrencies())
                        .build());
    }

    @ApiOperation("Toggle account allow negative")
    @PostMapping(ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL)
    public ResponseEntity<?> toggleAccountAllowNegative(
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) @NotBlank String accountId,
            Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL, accountId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService
                                .toggleAllowNegative(accountId, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @ApiOperation("Toggle show in sum")
    @PostMapping(ACCOUNT_TOGGLE_SHOW_IN_SUM_URL)
    public ResponseEntity<?> toggleShowInSum(
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) @NotBlank String accountId,
            Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_TOGGLE_SHOW_IN_SUM_URL, accountId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService
                                .toggleShowInSum(accountId, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }
}
