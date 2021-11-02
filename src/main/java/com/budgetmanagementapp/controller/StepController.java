package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.StepRqModel;
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

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Step")
public class StepController {

    private final StepService stepService;

    @ApiOperation("Get all steps")
    @GetMapping(STEP_GET_ALL_STEPS_URL)
    public ResponseEntity<?> getAllSteps() {

        log.info(format(REQUEST_MSG, STEP_GET_ALL_STEPS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(stepService.getAllSteps())
                        .build());
    }

    @ApiOperation("Create step")
    @PostMapping(STEP_CREATE_URL)
    public ResponseEntity<?> createStep(@RequestBody @Valid StepRqModel request) {

        log.info(format(REQUEST_MSG, STEP_CREATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(stepService.createStep(request))
                        .build());
    }

    @ApiOperation("Update step")
    @PostMapping(STEP_UPDATE_URL)
    public ResponseEntity<?> updateStep(@RequestBody @Valid StepRqModel request,
                                        @ApiParam(
                                                name = "step-id",
                                                type = "string",
                                                example = "",
                                                required = true)
                                        @RequestParam(name = "step-id") String stepId) {

        log.info(format(REQUEST_MSG, STEP_UPDATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(stepService.updateStep(request, stepId))
                        .build());
    }

    @ApiOperation("Delete step")
    @PostMapping(STEP_DELETE_URL)
    public ResponseEntity<?> deleteStep(
            @ApiParam(
                    name = "step-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "step-id") String stepId) {

        log.info(format(REQUEST_PARAM_MSG, STEP_DELETE_URL, stepId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(stepService.deleteStep(stepId))
                        .build());
    }
}
