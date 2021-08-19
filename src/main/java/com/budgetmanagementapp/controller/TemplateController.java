package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

import com.budgetmanagementapp.model.*;
import com.budgetmanagementapp.service.TemplateService;
import com.budgetmanagementapp.utility.TransactionType;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping(TEMPLATE_CREATE_INCOME_URL)
    public ResponseEntity<?> createIncomeTemplate(@RequestBody @Valid InOutRqModel requestBody,
                                                  Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_INCOME_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(templateService.createTemplate(
                                requestBody,
                                TransactionType.INCOME,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TEMPLATE_CREATE_OUTGOING_URL)
    public ResponseEntity<?> createOutcomeTemplate(@RequestBody @Valid InOutRqModel requestBody,
                                                   Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_OUTGOING_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(templateService.createTemplate(
                                requestBody,
                                TransactionType.OUTGOING,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TEMPLATE_CREATE_TRANSFER_URL)
    public ResponseEntity<?> createTransferTemplate(@RequestBody @Valid TransferRqModel requestBody,
                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_TRANSFER_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(templateService.createTemplate(
                                requestBody,
                                TransactionType.TRANSFER,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TEMPLATE_CREATE_DEBT_IN_URL)
    public ResponseEntity<?> createDebtInTemplate(@RequestBody @Valid DebtRqModel requestBody,
                                                  Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_DEBT_IN_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(templateService.createTemplate(
                                requestBody,
                                TransactionType.DEBT_IN,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TEMPLATE_CREATE_DEBT_OUT_URL)
    public ResponseEntity<?> createDebtOutTemplate(@RequestBody @Valid DebtRqModel requestBody,
                                                   Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_CREATE_DEBT_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(templateService.createTemplate(
                                requestBody,
                                TransactionType.DEBT_OUT,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @GetMapping(TEMPLATE_GET_ALL_TEMPLATES_URL)
    public ResponseEntity<?> getAllTemplates(Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_GET_ALL_TEMPLATES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(templateService.getAllTemplatesByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }


    @PostMapping(TEMPLATE_UPDATE_IN_OUT_URL)
    public ResponseEntity<?> updateInOutTemplate(@RequestBody @Valid UpdateInOutRqModel requestBody,
                                                 Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_UPDATE_IN_OUT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(templateService.updateTemplate(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TEMPLATE_UPDATE_TRANSFER_URL)
    public ResponseEntity<?> updateTransferTemplate(@RequestBody @Valid UpdateTransferRqModel requestBody,
                                                    Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_UPDATE_TRANSFER_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(templateService.updateTemplate(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TEMPLATE_UPDATE_DEBT_URL)
    public ResponseEntity<?> updateDebtTemplate(@RequestBody @Valid UpdateDebtRqModel requestBody,
                                                Authentication auth) {

        log.info(format(REQUEST_MSG, TEMPLATE_UPDATE_DEBT_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(templateService.updateTemplate(
                                requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }

    @PostMapping(TEMPLATE_DELETE_TEMPLATES_URL)
    public ResponseEntity<?> deleteTemplateTransactions(@RequestBody @Valid DeleteTransactionRqModel requestBody,
                                                        Authentication auth){

        log.info(format(REQUEST_MSG, TEMPLATE_DELETE_TEMPLATES_URL, requestBody));

        return ResponseEntity.ok(
                ResponseModel.builder()
                .status(HttpStatus.OK)
                .body(templateService.deleteTemplateById(
                        ((UserDetails) auth.getPrincipal()).getUsername(),
                        requestBody.getTransactionIds())
        ).build());
    }

}
