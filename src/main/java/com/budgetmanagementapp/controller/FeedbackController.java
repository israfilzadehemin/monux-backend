package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACK_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACK_GET_ALL_FEEDBACKS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACK_GET_FEEDBACK_BY_ID_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import com.budgetmanagementapp.service.FeedbackService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Feedback")
public class FeedbackController {

    private static final String REQUEST_PARAM_FEEDBACK_ID = "feedback-id";
    private final FeedbackService feedbackService;

    @ApiOperation("Create feedback")
    @PostMapping(FEEDBACK_CREATE_URL)
    public ResponseEntity<ResponseModel<FeedbackRsModel>> createFeedback(
            @RequestBody @Valid FeedbackRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACK_CREATE_URL, requestBody);
        var response =
                ResponseModel.of(
                        feedbackService.createFeedback(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        CREATED);

        log.info(RESPONSE_MSG, FEEDBACK_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all feedbacks")
    @GetMapping(FEEDBACK_GET_ALL_FEEDBACKS_URL)
    public ResponseEntity<ResponseModel<List<FeedbackRsModel>>> getAllFeedbacks(Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACK_GET_ALL_FEEDBACKS_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        feedbackService.getFeedbacksByUser(((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(RESPONSE_MSG, FEEDBACK_GET_ALL_FEEDBACKS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get feedback by id")
    @GetMapping(FEEDBACK_GET_FEEDBACK_BY_ID_URL)
    public ResponseEntity<ResponseModel<FeedbackRsModel>> getFeedbackById(
            @ApiParam(name = REQUEST_PARAM_FEEDBACK_ID, type = "string", example = "", required = true)
            @RequestParam(name = REQUEST_PARAM_FEEDBACK_ID) @NotBlank String feedbackId, Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACK_GET_FEEDBACK_BY_ID_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        feedbackService.getFeedbackById(feedbackId, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(REQUEST_MSG, FEEDBACK_GET_FEEDBACK_BY_ID_URL, response);
        return ResponseEntity.ok(response);
    }

}
