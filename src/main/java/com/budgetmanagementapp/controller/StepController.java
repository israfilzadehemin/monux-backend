package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static com.budgetmanagementapp.utility.UrlConstant.STEPS_URL;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.step.StepRqModel;
import com.budgetmanagementapp.model.step.StepRsModel;
import com.budgetmanagementapp.service.StepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@Tag(name = "Step", description = "Step operations")
@RequestMapping(STEPS_URL)
public class StepController {

    private final StepService stepService;

    @Operation(description = "Get all steps")
    @GetMapping
    public ResponseEntity<ResponseModel<List<StepRsModel>>> getAllSteps(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, STEPS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(stepService.getAllSteps(language), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEPS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Create step")
    @PostMapping
    public ResponseEntity<ResponseModel<StepRsModel>> createStep(@RequestBody @Valid StepRqModel request) {

        log.info(REQUEST_MSG, STEPS_URL, request);
        var response = ResponseModel.of(stepService.createStep(request), HttpStatus.CREATED);

        log.info(RESPONSE_MSG, STEPS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update step")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<StepRsModel>> updateStep(
            @RequestBody @Valid StepRqModel request,
            @PathVariable("id") String stepId) {

        log.info(REQUEST_MSG, STEPS_URL + PATH_ID, request);
        var response = ResponseModel.of(stepService.updateStep(request, stepId), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEPS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Delete step")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<StepRsModel>> deleteStep(
            @PathVariable(name = "id") String stepId) {

        log.info(REQUEST_MSG, STEPS_URL + PATH_ID, stepId);
        var response = ResponseModel.of(stepService.deleteStep(stepId), HttpStatus.OK);

        log.info(RESPONSE_MSG, STEPS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }
}
