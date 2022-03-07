package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.step.StepRqModel;
import com.budgetmanagementapp.model.step.StepRsModel;
import com.budgetmanagementapp.service.StepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static com.budgetmanagementapp.utility.UrlConstant.STEPS_URL;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Step")
@RequestMapping(STEPS_URL)
public class StepController {

    private final StepService stepService;

    @ApiOperation("Get all steps")
    @GetMapping
    public ResponseEntity<ResponseModel<List<StepRsModel>>> getAllSteps(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, STEPS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(stepService.getAllSteps(language), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEPS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create step")
    @PostMapping
    public ResponseEntity<ResponseModel<StepRsModel>> createStep(@RequestBody @Valid StepRqModel request) {

        log.info(REQUEST_MSG, STEPS_URL, request);
        var response = ResponseModel.of(stepService.createStep(request), HttpStatus.CREATED);

        log.info(RESPONSE_MSG, STEPS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update step")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<StepRsModel>> updateStep(
            @RequestBody @Valid StepRqModel request,
            @PathVariable("id") String stepId) {

        log.info(REQUEST_MSG, STEPS_URL + PATH_ID, request);
        var response = ResponseModel.of(stepService.updateStep(request, stepId), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEPS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete step")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<StepRsModel>> deleteStep(
            @PathVariable(name = "id") String stepId) {

        log.info(REQUEST_MSG, STEPS_URL + PATH_ID, stepId);
        var response = ResponseModel.of(stepService.deleteStep(stepId), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEPS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }
}
