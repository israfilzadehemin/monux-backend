package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.AccountTypeRsModel;
import com.budgetmanagementapp.model.account.CurrencyRsModel;
import com.budgetmanagementapp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_CURRENCIES_URL;
import static org.springframework.http.HttpStatus.OK;


@RestController
@AllArgsConstructor
@Log4j2
@Validated
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Account Details")
public class AccountDetailsController {
    private final AccountService accountService;

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


}
