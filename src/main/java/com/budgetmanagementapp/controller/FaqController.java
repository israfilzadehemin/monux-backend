package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.faq.FaqRqModel;
import com.budgetmanagementapp.model.faq.UpdateFaqRqModel;
import com.budgetmanagementapp.service.FaqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "FAQ")
public class FaqController {

    private final FaqService faqService;

    @ApiOperation("Get all faqs")
    @GetMapping(FAQ_GET_ALL_FAQS_URL)
    public ResponseEntity<?> getAllFaqs() {
        log.info(format(REQUEST_MSG, FAQ_GET_ALL_FAQS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(faqService.getAllFaqs())
                        .build());
    }

    @ApiOperation("Get faq by id")
    @GetMapping(FAQ_GET_FAQ_BY_ID_URL)
    public ResponseEntity<?> getFaqById(
            @ApiParam(
                    name = "faq-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "faq-id") String faqId) {
        log.info(format(REQUEST_MSG, FAQ_GET_FAQ_BY_ID_URL, faqId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(faqService.getFaqById(faqId))
                        .build());
    }

    @ApiOperation("Create faq")
    @PostMapping(FAQ_CREATE_URL)
    public ResponseEntity<?> createFaq(@RequestBody @Valid FaqRqModel request) {
        log.info(format(REQUEST_MSG, FAQ_CREATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(faqService.createFaq(request))
                        .build());
    }

    @ApiOperation("Update faq")
    @PostMapping(FAQ_UPDATE_URL)
    public ResponseEntity<?> updateFaq(@RequestBody @Valid UpdateFaqRqModel request) {
        log.info(format(REQUEST_MSG, FAQ_UPDATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(faqService.updateFaq(request))
                        .build());
    }

    @ApiOperation("Delete faq")
    @PostMapping(FAQ_DELETE_URL)
    public ResponseEntity<?> deleteFaq(
            @ApiParam(
                    name = "faq-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam("faq-id") String faqId) {
        log.info(format(REQUEST_PARAM_MSG, FAQ_DELETE_URL, faqId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(faqService.deleteFaq(faqId))
                        .build());
    }
}
