package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.ACCOUNT_GET_ALL_CURRENCIES_URL;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.account.AccountTypeRsModel;
import com.budgetmanagementapp.model.account.CurrencyRsModel;
import com.budgetmanagementapp.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@Log4j2
@Validated
@Tag(name = "Account Details", description = "Operations which are indirectly related with Account")
public class AccountDetailsController {
    private final AccountService accountService;

    @Operation(description = "Get all account types")
    @GetMapping(ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL)
    public ResponseEntity<ResponseModel<List<AccountTypeRsModel>>> getAllAccountTypes() {
        log.info(REQUEST_MSG, ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(accountService.getAllAccountTypes(), OK);

        log.info(RESPONSE_MSG, ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get all currencies")
    @GetMapping(ACCOUNT_GET_ALL_CURRENCIES_URL)
    public ResponseEntity<ResponseModel<List<CurrencyRsModel>>> getAllCurrencies() {
        log.info(REQUEST_MSG, ACCOUNT_GET_ALL_CURRENCIES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(accountService.getAllCurrencies(), OK);

        log.info(RESPONSE_MSG, ACCOUNT_GET_ALL_CURRENCIES_URL, response);
        return ResponseEntity.ok(response);
    }


}
