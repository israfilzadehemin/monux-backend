package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.model.*;
import com.budgetmanagementapp.model.transaction.*;
import com.budgetmanagementapp.model.account.UpdateDebtRqModel;
import com.budgetmanagementapp.model.account.UpdateInOutRqModel;
import com.budgetmanagementapp.model.transfer.TransferRqModel;
import com.budgetmanagementapp.model.transfer.TransferRsModel;
import com.budgetmanagementapp.model.transfer.UpdateTransferRqModel;
import com.budgetmanagementapp.service.TemplateService;
import com.budgetmanagementapp.utility.TransactionType;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Template")
public class TemplateController {

    private final TemplateService templateService;

    @ApiOperation("Create income template")
    @PostMapping(TEMPLATE_CREATE_INCOME_URL)
    public ResponseEntity<ResponseModel<TransactionRsModel>> createIncomeTemplate(@RequestBody @Valid InOutRqModel requestBody,
                                                                                  Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_INCOME_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(templateService.createTemplate(
                                requestBody,
                                TransactionType.INCOME,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create outgoing template")
    @PostMapping(TEMPLATE_CREATE_OUTGOING_URL)
    public ResponseEntity<ResponseModel<TransactionRsModel>> createOutgoingTemplate(@RequestBody @Valid InOutRqModel requestBody,
                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_OUTGOING_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(templateService.createTemplate(
                                requestBody,
                                TransactionType.OUTGOING,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create transfer template")
    @PostMapping(TEMPLATE_CREATE_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> createTransferTemplate(@RequestBody @Valid TransferRqModel requestBody,
                                                                                 Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_TRANSFER_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(templateService.createTemplate(
                                requestBody,
                                TransactionType.TRANSFER,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create debt in template")
    @PostMapping(TEMPLATE_CREATE_DEBT_IN_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtInTemplate(@RequestBody @Valid DebtRqModel requestBody,
                                                                           Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_DEBT_IN_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(templateService.createTemplate(
                                requestBody,
                                TransactionType.DEBT_IN,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Create debt out template")
    @PostMapping(TEMPLATE_CREATE_DEBT_OUT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtOutTemplate(@RequestBody @Valid DebtRqModel requestBody,
                                                   Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_DEBT_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(templateService.createTemplate(
                                requestBody,
                                TransactionType.DEBT_OUT,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.CREATED));
    }

    @ApiOperation("Get all templates")
    @GetMapping(TEMPLATE_GET_ALL_TEMPLATES_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getAllTemplates(Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_GET_ALL_TEMPLATES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(templateService.getAllTemplatesByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

    @ApiOperation("Update income and outgoing templates")
    @PostMapping(TEMPLATE_UPDATE_IN_OUT_URL)
    public ResponseEntity<ResponseModel<InOutRsModel>> updateInOutTemplate(@RequestBody @Valid UpdateInOutRqModel requestBody,
                                                 Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_UPDATE_IN_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(templateService.updateTemplate(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

    @ApiOperation("Update transfer template")
    @PostMapping(TEMPLATE_UPDATE_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> updateTransferTemplate(@RequestBody @Valid UpdateTransferRqModel requestBody,
                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_UPDATE_TRANSFER_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(templateService.updateTemplate(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

    @ApiOperation("Update debt in and out template")
    @PostMapping(TEMPLATE_UPDATE_DEBT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> updateDebtTemplate(@RequestBody @Valid UpdateDebtRqModel requestBody,
                                                Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_UPDATE_DEBT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(templateService.updateTemplate(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()), HttpStatus.OK));
    }

    @ApiOperation("Delete template")
    @PostMapping(TEMPLATE_DELETE_TEMPLATES_URL)
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> deleteTemplateTransactions(@RequestBody @Valid DeleteTransactionRqModel requestBody,
                                                        Authentication auth){

        log.info(format(REQUEST_MSG, TEMPLATE_DELETE_TEMPLATES_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.of(templateService.deleteTemplateById(
                        ((UserDetails) auth.getPrincipal()).getUsername(),
                        requestBody.getTransactionIds()), HttpStatus.OK));
    }

}
