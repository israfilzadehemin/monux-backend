package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNTS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_TOGGLE_SHOW_IN_SUM_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_UPDATE_BALANCE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.AccountRqModel;
import com.budgetmanagementapp.model.account.AccountRsModel;
import com.budgetmanagementapp.model.account.UpdateAccountModel;
import com.budgetmanagementapp.model.account.UpdateBalanceRqModel;
import com.budgetmanagementapp.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Validated
@Tag(name = "Account", description = "Account related operations")
@RequestMapping(ACCOUNTS_URL)
public class AccountController {
    private final AccountService accountService;

    @Operation(description = "Create account")
    @PostMapping
    public ResponseEntity<ResponseModel<AccountRsModel>> createAccount(@RequestBody AccountRqModel requestBody,
                                                                       @Parameter(hidden = true) Authentication auth) {
        requestBody.setUsername(((UserDetails) auth.getPrincipal()).getUsername());

        log.info(REQUEST_MSG, ACCOUNTS_URL, requestBody);
        var response = ResponseModel.of(accountService.createAccount(requestBody, false), CREATED);

        log.info(RESPONSE_MSG, ACCOUNTS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update account")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<AccountRsModel>> updateAccount(
            @RequestBody @Valid UpdateAccountModel requestBody,
            @PathVariable("id") String accountId, @Parameter(hidden = true) Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNTS_URL + PATH_ID, requestBody);
        var response = ResponseModel.of(
                accountService.updateAccount(requestBody, accountId, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNTS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update balance of account")
    @PutMapping(ACCOUNT_UPDATE_BALANCE_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> updateBalance(
            @RequestBody @Valid UpdateBalanceRqModel requestBody,
            @PathVariable("id") String accountId, @Parameter(hidden = true) Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNTS_URL + ACCOUNT_UPDATE_BALANCE_URL, requestBody);
        var response = ResponseModel.of(
                accountService.updateBalance(requestBody, accountId, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNTS_URL + ACCOUNT_UPDATE_BALANCE_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get all accounts")
    @GetMapping
    public ResponseEntity<ResponseModel<List<AccountRsModel>>> getAllAccounts(@Parameter(hidden = true) Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNTS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                accountService.getAllAccountsByUser(((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNTS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Toggle account allow negative")
    @PostMapping(ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> toggleAccountAllowNegative(
            @Parameter(example = "500de72f-7e...", required = true)
            @PathVariable(name = "id") @NotBlank String accountId,
            @Parameter(hidden = true) Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNTS_URL + ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL, accountId);
        var response = ResponseModel.of(accountService
                .toggleAllowNegative(accountId, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNTS_URL + ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Toggle show in sum")
    @PostMapping(ACCOUNT_TOGGLE_SHOW_IN_SUM_URL)
    public ResponseEntity<ResponseModel<AccountRsModel>> toggleShowInSum(
            @Parameter(example = "500de72f-7e...", required = true)
            @PathVariable(name = "id") @NotBlank String accountId,
            @Parameter(hidden = true) Authentication auth) {
        log.info(REQUEST_MSG, ACCOUNTS_URL + ACCOUNT_TOGGLE_SHOW_IN_SUM_URL, accountId);
        var response = ResponseModel.of(
                accountService.toggleShowInSum(accountId, ((UserDetails) auth.getPrincipal()).getUsername()), OK);

        log.info(RESPONSE_MSG, ACCOUNTS_URL + ACCOUNT_TOGGLE_SHOW_IN_SUM_URL, response);
        return ResponseEntity.ok(response);
    }
}
