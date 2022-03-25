package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACKS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import com.budgetmanagementapp.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Feedback", description = "Feedback operations")
@RequestMapping(FEEDBACKS_URL)
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(description = "Create feedback")
    @PostMapping
    public ResponseEntity<ResponseModel<FeedbackRsModel>> createFeedback(
            @RequestBody @Valid FeedbackRqModel requestBody, @Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACKS_URL, requestBody);
        var response =
                ResponseModel.of(
                        feedbackService.createFeedback(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        CREATED);

        log.info(RESPONSE_MSG, FEEDBACKS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get all feedbacks")
    @GetMapping
    public ResponseEntity<ResponseModel<List<FeedbackRsModel>>> getAllFeedbacks(@Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACKS_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        feedbackService.getFeedbacksByUser(((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(RESPONSE_MSG, FEEDBACKS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get feedback by id")
    @GetMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FeedbackRsModel>> getFeedbackById(
            @PathVariable(name = "id") @NotBlank String feedbackId, @Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, FEEDBACKS_URL + PATH_ID, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        feedbackService.getFeedbackById(feedbackId, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(REQUEST_MSG, FEEDBACKS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

}
