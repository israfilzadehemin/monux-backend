package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACK_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACK_GET_ALL_FEEDBACKS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FEEDBACK_GET_FEEDBACK_BY_ID_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.service.FeedbackService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
public class FeedbackController {

    private final FeedbackService feedbackService;

    private static final String REQUEST_PARAM_FEEDBACK_ID = "feedback-id";

    @PostMapping(FEEDBACK_CREATE_URL)
    public ResponseEntity<?> createFeedback(@RequestBody @Valid FeedbackRqModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, FEEDBACK_CREATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(feedbackService.createFeedback(requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @GetMapping(FEEDBACK_GET_ALL_FEEDBACKS_URL)
    public ResponseEntity<?> getAllFeedbacks(Authentication auth) {

        log.info(format(REQUEST_MSG, FEEDBACK_GET_ALL_FEEDBACKS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(feedbackService
                                .getFeedbacksByUser(((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @GetMapping(FEEDBACK_GET_FEEDBACK_BY_ID_URL)
    public ResponseEntity<?> getFeedbackById(
            @RequestParam(name = REQUEST_PARAM_FEEDBACK_ID) @NotBlank String feedbackId, Authentication auth) {

        log.info(format(REQUEST_MSG, FEEDBACK_GET_FEEDBACK_BY_ID_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(feedbackService.getFeedbackById(
                                feedbackId,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

}
