package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import com.budgetmanagementapp.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACKS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Feedback")
@RequestMapping(FEEDBACKS_URL)
public class FeedbackController {

    private final FeedbackService feedbackService;

    @ApiOperation("Create feedback")
    @PostMapping
    public ResponseEntity<ResponseModel<FeedbackRsModel>> createFeedback(
            @RequestBody @Valid FeedbackRqModel requestBody, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACKS_URL, requestBody);
        var response =
                ResponseModel.of(
                        feedbackService.createFeedback(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        CREATED);

        log.info(RESPONSE_MSG, FEEDBACKS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all feedbacks")
    @GetMapping
    public ResponseEntity<ResponseModel<List<FeedbackRsModel>>> getAllFeedbacks(@ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACKS_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        feedbackService.getFeedbacksByUser(((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(RESPONSE_MSG, FEEDBACKS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get feedback by id")
    @GetMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FeedbackRsModel>> getFeedbackById(
            @PathVariable(name = "id") @NotBlank String feedbackId, @ApiIgnore Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACKS_URL + PATH_ID, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        feedbackService.getFeedbackById(feedbackId, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(REQUEST_MSG, FEEDBACKS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

}
