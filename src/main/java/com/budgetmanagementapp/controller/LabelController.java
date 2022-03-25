package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.LABELS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.LABEL_BY_USER_URL;
import static com.budgetmanagementapp.utility.UrlConstant.LABEL_TOGGLE_VISIBILITY_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.label.LabelRqModel;
import com.budgetmanagementapp.model.label.LabelRsModel;
import com.budgetmanagementapp.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Label", description = "Label operations")
@RequestMapping(LABELS_URL)
public class LabelController {

    private final LabelService labelService;

    @Operation(description = "Create label")
    @PostMapping
    public ResponseEntity<ResponseModel<LabelRsModel>> createLabel(
            @RequestBody @Valid LabelRqModel requestBody, @Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, LABELS_URL, requestBody);
        var response =
                ResponseModel.of(
                        labelService.createLabel(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        CREATED);

        log.info(RESPONSE_MSG, LABELS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get all labels")
    @GetMapping
    public ResponseEntity<ResponseModel<List<LabelRsModel>>> getAllLabels(@Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, LABELS_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        labelService.getLabelsByUser(((UserDetails) auth.getPrincipal()).getUsername(), true),
                        OK);

        log.info(RESPONSE_MSG, LABELS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get labels of user")
    @GetMapping(LABEL_BY_USER_URL)
    public ResponseEntity<ResponseModel<List<LabelRsModel>>> getLabelsOfUser(@Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, LABELS_URL + LABEL_BY_USER_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        labelService.getLabelsByUser(((UserDetails) auth.getPrincipal()).getUsername(), false),
                        OK);

        log.info(RESPONSE_MSG, LABELS_URL + LABEL_BY_USER_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update label")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<LabelRsModel>> updateLabel(
            @RequestBody @Valid LabelRqModel requestBody,
            @PathVariable("id") String labelId, @Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, LABELS_URL + PATH_ID, requestBody);
        var response =
                ResponseModel.of(
                        labelService.updateLabel(requestBody, labelId, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(RESPONSE_MSG, LABELS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Toggle label visibility")
    @PostMapping(LABEL_TOGGLE_VISIBILITY_URL)
    public ResponseEntity<ResponseModel<LabelRsModel>> toggleVisibility(
            @PathVariable(name = "id") String labelId, @Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, LABELS_URL + LABEL_TOGGLE_VISIBILITY_URL, labelId);
        var response =
                ResponseModel.of(
                        labelService.toggleVisibility(labelId, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        return ResponseEntity.ok(response);
    }
}
