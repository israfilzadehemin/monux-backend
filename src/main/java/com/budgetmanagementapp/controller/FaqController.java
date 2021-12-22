package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.FAQ_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FAQ_DELETE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FAQ_GET_ALL_FAQS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FAQ_GET_FAQ_BY_ID_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FAQ_UPDATE_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.faq.FaqRqModel;
import com.budgetmanagementapp.model.faq.FaqRsModel;
import com.budgetmanagementapp.model.faq.UpdateFaqRqModel;
import com.budgetmanagementapp.service.FaqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "FAQ")
public class FaqController {

    private final FaqService faqService;

    @ApiOperation("Get all faqs")
    @GetMapping(FAQ_GET_ALL_FAQS_URL)
    public ResponseEntity<ResponseModel<List<FaqRsModel>>> getAllFaqs(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, FAQ_GET_ALL_FAQS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(faqService.getAllFaqs(language), OK);

        log.info(RESPONSE_MSG, FAQ_GET_ALL_FAQS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get faq by id")
    @GetMapping(FAQ_GET_FAQ_BY_ID_URL)
    public ResponseEntity<ResponseModel<FaqRsModel>> getFaqById(
            @ApiParam(name = "faq-id", type = "string", example = "", required = true)
            @RequestParam(name = "faq-id") String faqId,
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, FAQ_GET_FAQ_BY_ID_URL, faqId);
        var response = ResponseModel.of(faqService.getFaqById(faqId, language), OK);

        log.info(RESPONSE_MSG, FAQ_GET_FAQ_BY_ID_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create faq")
    @PostMapping(FAQ_CREATE_URL)
    public ResponseEntity<ResponseModel<FaqRsModel>> createFaq(@RequestBody @Valid FaqRqModel request) {

        log.info(REQUEST_MSG, FAQ_CREATE_URL, request);
        var response = ResponseModel.of(faqService.createFaq(request), CREATED);

        log.info(REQUEST_MSG, FAQ_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update faq")
    @PostMapping(FAQ_UPDATE_URL)
    public ResponseEntity<ResponseModel<FaqRsModel>> updateFaq(@RequestBody @Valid UpdateFaqRqModel request) {

        log.info(REQUEST_MSG, FAQ_UPDATE_URL, request);
        var response = ResponseModel.of(faqService.updateFaq(request), OK);

        log.info(RESPONSE_MSG, FAQ_UPDATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete faq")
    @PostMapping(FAQ_DELETE_URL)
    public ResponseEntity<ResponseModel<FaqRsModel>> deleteFaq(
            @ApiParam(name = "faq-id", type = "string", example = "", required = true)
            @RequestParam("faq-id") String faqId) {

        log.info(REQUEST_MSG, FAQ_DELETE_URL, faqId);
        var response = ResponseModel.of(faqService.deleteFaq(faqId), OK);

        log.info(RESPONSE_MSG, FAQ_DELETE_URL, response);
        return ResponseEntity.ok(response);
    }
}
