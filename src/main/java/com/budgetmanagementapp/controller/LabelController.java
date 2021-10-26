package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.LABEL_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.LABEL_GET_ALL_LABELS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.LABEL_GET_LABELS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.LABEL_TOGGLE_VISIBILITY_URL;
import static com.budgetmanagementapp.utility.UrlConstant.LABEL_UPDATE_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.label.LabelRqModel;
import com.budgetmanagementapp.model.label.UpdateLabelRqModel;
import com.budgetmanagementapp.service.LabelService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Label")
public class LabelController {

    private final LabelService labelService;

    private static final String REQUEST_PARAM_LABEL_ID = "label-id";

    @ApiOperation("Create label")
    @PostMapping(LABEL_CREATE_URL)
    public ResponseEntity<?> createLabel(@RequestBody @Valid LabelRqModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, LABEL_CREATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(labelService.createLabel(requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @ApiOperation("Get all labels")
    @GetMapping(LABEL_GET_ALL_LABELS_URL)
    public ResponseEntity<?> getAllLabels(Authentication auth) {

        log.info(format(REQUEST_MSG, LABEL_GET_ALL_LABELS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(labelService.getLabelsByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername(), true))
                        .build());
    }

    @ApiOperation("Get labels of user")
    @GetMapping(LABEL_GET_LABELS_URL)
    public ResponseEntity<?> getLabelsOfUser(Authentication auth) {

        log.info(format(REQUEST_MSG, LABEL_GET_LABELS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(labelService.getLabelsByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername(), false))
                        .build());
    }

    @ApiOperation("Update label")
    @PostMapping(LABEL_UPDATE_URL)
    public ResponseEntity<?> updateLabel(@RequestBody @Valid UpdateLabelRqModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, LABEL_UPDATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(labelService
                                .updateLabel(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @ApiOperation("Toggle label visibilty")
    @PostMapping(LABEL_TOGGLE_VISIBILITY_URL)
    public ResponseEntity<?> toggleVisibility(
            @RequestParam(name = REQUEST_PARAM_LABEL_ID) String labelId, Authentication auth) {
        log.info(format(REQUEST_MSG, LABEL_TOGGLE_VISIBILITY_URL, labelId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(labelService
                                .toggleVisibility(labelId, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }
}
