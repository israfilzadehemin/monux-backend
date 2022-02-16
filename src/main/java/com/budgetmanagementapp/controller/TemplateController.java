package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_IN;
import static com.budgetmanagementapp.utility.TransactionType.DEBT_OUT;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.OUTGOING;
import static com.budgetmanagementapp.utility.TransactionType.TRANSFER;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_BY_ID_DEBT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_BY_ID_IN_OUT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_BY_ID_TRANSFER_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_DEBT_IN_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_DEBT_OUT_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_INCOME_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_OUTGOING_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TEMPLATE_TRANSFER_URL;
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
import com.budgetmanagementapp.service.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Template")
@RequestMapping(TEMPLATES_URL)
public class TemplateController {

    private final TemplateService templateService;

    @ApiOperation("Create income template")
    @PostMapping(TEMPLATE_INCOME_URL)
    public ResponseEntity<ResponseModel<TransactionRsModel>> createIncomeTemplate(
            @RequestBody @Valid InOutRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_INCOME_URL, requestBody);
        var response = ResponseModel.of(
                templateService.createTemplate(
                        requestBody, INCOME, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_INCOME_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create outgoing template")
    @PostMapping(TEMPLATE_OUTGOING_URL)
    public ResponseEntity<ResponseModel<TransactionRsModel>> createOutgoingTemplate(
            @RequestBody @Valid InOutRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_OUTGOING_URL, requestBody);
        var response = ResponseModel.of(
                templateService.createTemplate(
                        requestBody, OUTGOING, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_OUTGOING_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create transfer template")
    @PostMapping(TEMPLATE_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> createTransferTemplate(
            @RequestBody @Valid TransferRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_TRANSFER_URL, requestBody);
        var response = ResponseModel.of(
                templateService.createTemplate(
                        requestBody, TRANSFER, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_TRANSFER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create debt in template")
    @PostMapping(TEMPLATE_DEBT_IN_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtInTemplate(
            @RequestBody @Valid DebtRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_DEBT_IN_URL, requestBody);
        var response = ResponseModel.of(
                templateService.createTemplate(
                        requestBody, DEBT_IN, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);


        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_DEBT_IN_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create debt out template")
    @PostMapping(TEMPLATE_DEBT_OUT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> createDebtOutTemplate(
            @RequestBody @Valid DebtRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_DEBT_OUT_URL, requestBody);
        var response = ResponseModel.of(
                templateService.createTemplate(
                        requestBody, DEBT_OUT, ((UserDetails) auth.getPrincipal()).getUsername()),
                CREATED);

        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_DEBT_OUT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all templates")
    @GetMapping
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> getAllTemplates(@ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(
                templateService.getAllTemplatesByUser(((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TEMPLATES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update income and outgoing templates")
    @PutMapping(TEMPLATE_BY_ID_IN_OUT_URL)
    public ResponseEntity<ResponseModel<InOutRsModel>> updateInOutTemplate(
            @RequestBody @Valid InOutRqModel requestBody, @ApiIgnore Authentication auth,
            @PathVariable("id") String templateId) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_BY_ID_IN_OUT_URL, requestBody);
        var response = ResponseModel.of(
                templateService.updateTemplate(requestBody, templateId, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_BY_ID_IN_OUT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update transfer template")
    @PutMapping(TEMPLATE_BY_ID_TRANSFER_URL)
    public ResponseEntity<ResponseModel<TransferRsModel>> updateTransferTemplate(
            @RequestBody @Valid TransferRqModel requestBody,
            @PathVariable("id") String templateId, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_BY_ID_TRANSFER_URL, requestBody);
        var response = ResponseModel.of(
                templateService.updateTemplate(requestBody, templateId, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_BY_ID_TRANSFER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update debt in and out template")
    @PutMapping(TEMPLATE_BY_ID_DEBT_URL)
    public ResponseEntity<ResponseModel<DebtRsModel>> updateDebtTemplate(
            @RequestBody @Valid DebtRqModel requestBody,
            @PathVariable("id") String templateId, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL + TEMPLATE_BY_ID_DEBT_URL, requestBody);
        var response = ResponseModel.of(
                templateService.updateTemplate(requestBody, templateId, ((UserDetails) auth.getPrincipal()).getUsername()),
                OK);

        log.info(RESPONSE_MSG, TEMPLATES_URL + TEMPLATE_BY_ID_DEBT_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete template")
    @DeleteMapping
    public ResponseEntity<ResponseModel<List<TransactionRsModel>>> deleteTemplateTransactions(
            @RequestBody @Valid DeleteTransactionRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, TEMPLATES_URL, requestBody);
        var response = ResponseModel.of(
                templateService.deleteTemplateById(
                        ((UserDetails) auth.getPrincipal()).getUsername(), requestBody.getTransactionIds()),
                OK);

        log.info(RESPONSE_MSG, TEMPLATES_URL, response);
        return ResponseEntity.ok(response);
    }

}
