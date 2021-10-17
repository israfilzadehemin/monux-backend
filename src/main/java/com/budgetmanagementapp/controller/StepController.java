package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.StepRqModel;
import com.budgetmanagementapp.service.StepService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
public class StepController {

    private final StepService stepService;

    @GetMapping(STEP_GET_ALL_STEPS_URL)
    public ResponseEntity<?> getAllSteps() {

        log.info(format(REQUEST_MSG, STEP_GET_ALL_STEPS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(stepService.getAllSteps())
                        .build());
    }

    @PostMapping(STEP_CREATE_URL)
    public ResponseEntity<?> createStep(@RequestBody StepRqModel request) {

        log.info(format(REQUEST_MSG, STEP_CREATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(stepService.createStep(request))
                        .build());
    }

    @PostMapping(STEP_UPDATE_URL)
    public ResponseEntity<?> updateStep(@RequestBody StepRqModel request,
                                        @RequestParam(name = "step-id") String stepId) {

        log.info(format(REQUEST_MSG, STEP_UPDATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(stepService.updateStep(request, stepId))
                        .build());
    }

    @PostMapping(STEP_DELETE_URL)
    public ResponseEntity<?> deleteStep(@RequestParam(name = "step-id") String stepId) {

        log.info(format(REQUEST_PARAM_MSG, STEP_DELETE_URL, stepId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(stepService.deleteStep(stepId))
                        .build());
    }

}
