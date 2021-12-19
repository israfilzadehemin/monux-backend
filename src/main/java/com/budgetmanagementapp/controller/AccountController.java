package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_ACCOUNTS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_CURRENCIES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_TOGGLE_SHOW_IN_SUM_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_UPDATE_BALANCE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_UPDATE_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.model.account.AccountRsModel;
import com.budgetmanagementapp.model.account.AccountTypeRsModel;
import com.budgetmanagementapp.model.account.CurrencyRsModel;
import com.budgetmanagementapp.model.account.UpdateAccountModel;
import com.budgetmanagementapp.model.account.UpdateBalanceModel;
import com.budgetmanagementapp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Validated
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Account")
public class AccountController {
    private static final String REQUEST_PARAM_ACCOUNT_ID = "account-id";
    private final AccountService accountService;

    @ApiOperation("Create account")
    @PostMapping(ACCOUNT_CREATE_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> createAccount(@RequestBody AccountRqModel requestBody,
                                                                       Authentication auth) {
        requestBody.setUsername(((UserDetails) auth.getPrincipal()).getUsername());

        log.info(REQUEST_MSG, ACCOUNT_CREATE_URL, requestBody);
        var response = ResponseModel.of(accountService.createAccount(requestBody, false), CREATED);

        log.info(RESPONSE_MSG, ACCOUNT_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update account")
    @PostMapping(ACCOUNT_UPDATE_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> updateAccount(
            @RequestBody @Valid UpdateAccountModel requestBody, Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNT_UPDATE_URL, requestBody);
        var response = ResponseModel.of(
                accountService.updateAccount(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNT_UPDATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update balance of account")
    @PostMapping(ACCOUNT_UPDATE_BALANCE_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> updateBalance(
            @RequestBody @Valid UpdateBalanceModel requestBody, Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNT_UPDATE_BALANCE_URL, requestBody);
        var response = ResponseModel.of(
                accountService.updateBalance(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNT_UPDATE_BALANCE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all accounts")
    @GetMapping(ACCOUNT_GET_ALL_ACCOUNTS_URL)
    public ResponseEntity<ResponseModel<List<AccountRsModel>>> getAllAccounts(Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNT_GET_ALL_ACCOUNTS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                accountService.getAllAccountsByUser(((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNT_GET_ALL_ACCOUNTS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all account types")
    @GetMapping(ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL)
    public ResponseEntity<ResponseModel<List<AccountTypeRsModel>>> getAllAccountTypes() {
        log.info(REQUEST_MSG, ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(accountService.getAllAccountTypes(), OK);

        log.info(RESPONSE_MSG, ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all currencies")
    @GetMapping(ACCOUNT_GET_ALL_CURRENCIES_URL)
    public ResponseEntity<ResponseModel<List<CurrencyRsModel>>> getAllCurrencies() {
        log.info(REQUEST_MSG, ACCOUNT_GET_ALL_CURRENCIES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(accountService.getAllCurrencies(), OK);

        log.info(RESPONSE_MSG, ACCOUNT_GET_ALL_CURRENCIES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Toggle account allow negative")
    @PostMapping(ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> toggleAccountAllowNegative(
            @ApiParam(name = REQUEST_PARAM_ACCOUNT_ID, type = "string", example = "500de72f-7e...", required = true)
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) @NotBlank String accountId,
            Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL, accountId);
        var response = ResponseModel.of(accountService
                .toggleAllowNegative(accountId, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Toggle show in sum")
    @PostMapping(ACCOUNT_TOGGLE_SHOW_IN_SUM_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> toggleShowInSum(
            @ApiParam(name = REQUEST_PARAM_ACCOUNT_ID, type = "string", example = "500de72f-7e...", required = true)
            @RequestParam(name = REQUEST_PARAM_ACCOUNT_ID) @NotBlank String accountId,
            Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNT_TOGGLE_SHOW_IN_SUM_URL, accountId);
        var response = ResponseModel.of(
                accountService.toggleShowInSum(accountId, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNT_TOGGLE_SHOW_IN_SUM_URL, response);
        return ResponseEntity.ok(response);
    }
}
