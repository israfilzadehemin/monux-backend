package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.label.LabelRqModel;
import com.budgetmanagementapp.model.label.LabelRsModel;
import com.budgetmanagementapp.model.label.UpdateLabelRqModel;
import com.budgetmanagementapp.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Label")
public class LabelController {

    private static final String REQUEST_PARAM_LABEL_ID = "label-id";
    private final LabelService labelService;

    @ApiOperation("Create label")
    @PostMapping(LABEL_CREATE_URL)
    public ResponseEntity<ResponseModel<LabelRsModel>> createLabel(
            @RequestBody @Valid LabelRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, LABEL_CREATE_URL, requestBody);
        var response =
                ResponseModel.of(
                        labelService.createLabel(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        CREATED);

        log.info(RESPONSE_MSG, LABEL_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all labels")
    @GetMapping(LABEL_GET_ALL_LABELS_URL)
    public ResponseEntity<ResponseModel<List<LabelRsModel>>> getAllLabels(Authentication auth) {

        log.info(REQUEST_MSG, LABEL_GET_ALL_LABELS_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        labelService.getLabelsByUser(((UserDetails) auth.getPrincipal()).getUsername(), true),
                        OK);

        log.info(RESPONSE_MSG, LABEL_GET_ALL_LABELS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get labels of user")
    @GetMapping(LABEL_GET_LABELS_URL)
    public ResponseEntity<ResponseModel<List<LabelRsModel>>> getLabelsOfUser(Authentication auth) {

        log.info(REQUEST_MSG, LABEL_GET_LABELS_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        labelService.getLabelsByUser(((UserDetails) auth.getPrincipal()).getUsername(), false),
                        OK);

        log.info(RESPONSE_MSG, LABEL_GET_LABELS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update label")
    @PostMapping(LABEL_UPDATE_URL)
    public ResponseEntity<ResponseModel<LabelRsModel>> updateLabel(
            @RequestBody @Valid UpdateLabelRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, LABEL_UPDATE_URL, requestBody);
        var response =
                ResponseModel.of(
                        labelService.updateLabel(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(RESPONSE_MSG, LABEL_UPDATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Toggle label visibility")
    @PostMapping(LABEL_TOGGLE_VISIBILITY_URL)
    public ResponseEntity<ResponseModel<LabelRsModel>> toggleVisibility(
            @ApiParam(name = REQUEST_PARAM_LABEL_ID, type = "string", required = true)
            @RequestParam(name = REQUEST_PARAM_LABEL_ID) String labelId, Authentication auth) {

        log.info(REQUEST_MSG, LABEL_TOGGLE_VISIBILITY_URL, labelId);
        var response =
                ResponseModel.of(
                        labelService.toggleVisibility(labelId, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        return ResponseEntity.ok(response);

    }
}
