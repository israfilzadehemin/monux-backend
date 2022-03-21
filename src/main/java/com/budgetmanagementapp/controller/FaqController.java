package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.faq.FaqRqModel;
import com.budgetmanagementapp.model.faq.FaqRsModel;
import com.budgetmanagementapp.service.FaqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.FAQ_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "FAQ")
@RequestMapping(FAQ_URL)
public class FaqController {

    private final FaqService faqService;

    @ApiOperation("Get all faqs")
    @GetMapping
    public ResponseEntity<ResponseModel<List<FaqRsModel>>> getAllFaqs(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, FAQ_URL, NO_BODY_MSG);
        var response = ResponseModel.of(faqService.getAllFaqs(language), OK);

        log.info(RESPONSE_MSG, FAQ_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get faq by id")
    @GetMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FaqRsModel>> getFaqById(
            @Parameter(name = "faq-id")
            @PathVariable(name = "id") String faqId,
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, FAQ_URL + PATH_ID, faqId);
        var response = ResponseModel.of(faqService.getFaqById(faqId, language), OK);

        log.info(RESPONSE_MSG, FAQ_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create faq")
    @PostMapping
    public ResponseEntity<ResponseModel<FaqRsModel>> createFaq(@RequestBody @Valid FaqRqModel request) {

        log.info(REQUEST_MSG, FAQ_URL, request);
        var response = ResponseModel.of(faqService.createFaq(request), CREATED);

        log.info(REQUEST_MSG, FAQ_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update faq")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FaqRsModel>> updateFaq(
            @RequestBody @Valid FaqRqModel request,
            @PathVariable("id") String faqId) {

        log.info(REQUEST_MSG, FAQ_URL + PATH_ID, request);
        var response = ResponseModel.of(faqService.updateFaq(request, faqId), OK);

        log.info(RESPONSE_MSG, FAQ_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete faq")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FaqRsModel>> deleteFaq(
            @PathVariable("id") String faqId) {

        log.info(REQUEST_MSG, FAQ_URL + PATH_ID, faqId);
        var response = ResponseModel.of(faqService.deleteFaq(faqId), OK);

        log.info(RESPONSE_MSG, FAQ_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }
}
