package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_ACCOUNTS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_TOGGLE_SHOW_IN_SUM_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_UPDATE_BALANCE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_UPDATE_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.AccountRequestModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
import com.budgetmanagementapp.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
public class AccountController {
    private final AccountService accountService;

    private static final String REQUEST_PARAM_ACCOUNT_ID = "account-id";


    @PostMapping(ACCOUNT_CREATE_URL)
    public ResponseEntity<?> createAccount(@RequestBody AccountRequestModel accountRequestModel, Authentication auth) {
        accountRequestModel.setUsername(((UserDetails) auth.getPrincipal()).getUsername());

        log.info(format(REQUEST_MSG, ACCOUNT_CREATE_URL, accountRequestModel));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(accountService.createAccount(accountRequestModel, false))
                        .build());

    }

    @PostMapping(ACCOUNT_UPDATE_URL)
    public ResponseEntity<?> updateAccount(@RequestBody UpdateAccountModel accountModel, Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_UPDATE_URL, accountModel));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService
                                .updateAccount(accountModel, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @PostMapping(ACCOUNT_UPDATE_BALANCE_URL)
    public ResponseEntity<?> updateBalance(@RequestBody UpdateBalanceModel balanceModel, Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_UPDATE_BALANCE_URL, balanceModel));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService
                                .updateBalance(balanceModel, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @GetMapping(ACCOUNT_GET_ALL_ACCOUNTS_URL)
    public ResponseEntity<?> getAllAccounts(Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_GET_ALL_ACCOUNTS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService.getAllAccountsByUser(((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @PostMapping(ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL)
    public ResponseEntity<?> toggleAccountAllowNegative(@RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) String accountId,
                                                        Authentication auth) {

        log.info(format(REQUEST_MSG, ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL, accountId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(accountService
                                .toggleAllowNegative(accountId, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @PostMapping(ACCOUNT_TOGGLE_SHOW_IN_SUM_URL)
    public ResponseEntity<?> toggleShowInSum(@RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) String accountId,
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