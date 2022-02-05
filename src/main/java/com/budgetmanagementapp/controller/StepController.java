package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.step.StepRqModel;
import com.budgetmanagementapp.model.step.StepRsModel;
import com.budgetmanagementapp.model.step.UpdateStepRqModel;
import com.budgetmanagementapp.service.StepService;
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
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Step")
public class StepController {

    private final StepService stepService;

    @ApiOperation("Get all steps")
    @GetMapping(STEP_GET_ALL_STEPS_URL)
    public ResponseEntity<ResponseModel<List<StepRsModel>>> getAllSteps(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, STEP_GET_ALL_STEPS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(stepService.getAllSteps(language), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEP_GET_ALL_STEPS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create step")
    @PostMapping(STEP_CREATE_URL)
    public ResponseEntity<ResponseModel<StepRsModel>> createStep(@RequestBody @Valid StepRqModel request) {

        log.info(REQUEST_MSG, STEP_CREATE_URL, request);
        var response = ResponseModel.of(stepService.createStep(request), HttpStatus.CREATED);

        log.info(RESPONSE_MSG, STEP_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update step")
    @PostMapping(STEP_UPDATE_URL)
    public ResponseEntity<ResponseModel<StepRsModel>> updateStep(@RequestBody @Valid UpdateStepRqModel request) {

        log.info(REQUEST_MSG, STEP_UPDATE_URL, request);
        var response = ResponseModel.of(stepService.updateStep(request), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEP_UPDATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete step")
    @PostMapping(STEP_DELETE_URL)
    public ResponseEntity<ResponseModel<StepRsModel>> deleteStep(
            @ApiParam(name = "step-id", type = "string", required = true)
            @RequestParam(name = "step-id") String stepId) {

        log.info(REQUEST_MSG, STEP_DELETE_URL, stepId);
        var response = ResponseModel.of(stepService.deleteStep(stepId), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEP_DELETE_URL, response);
        return ResponseEntity.ok(response);
    }
}
