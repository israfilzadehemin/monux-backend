package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import com.budgetmanagementapp.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static com.budgetmanagementapp.utility.UrlConstant.PLANS_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Plan")
@RequestMapping(PLANS_URL)
public class PlanController {

    private final PlanService planService;

    @ApiOperation("Get all plans")
    @GetMapping
    public ResponseEntity<ResponseModel<List<PlanRsModel>>> getAllPlans(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, PLANS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(planService.getAllPlans(language), OK);

        log.info(RESPONSE_MSG, PLANS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Add plan")
    @PostMapping
    public ResponseEntity<ResponseModel<PlanRsModel>> addPlan(@RequestBody @Valid PlanRqModel request) {

        log.info(REQUEST_MSG, PLANS_URL, request);
        var response = ResponseModel.of(planService.addPlan(request), CREATED);

        log.info(RESPONSE_MSG, PLANS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update plan")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<PlanRsModel>> updatePlan(
            @RequestBody @Valid PlanRqModel request,
            @PathVariable("id") String planId) {

        log.info(REQUEST_MSG, PLANS_URL + PATH_ID, request);
        var response = ResponseModel.of(planService.updatePlan(request, planId), OK);

        log.info(REQUEST_MSG, PLANS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete plan")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<PlanRsModel>> deletePlan(
            @ApiParam(name = "plan-id", type = "string", required = true)
            @PathVariable("id") String planId) {

        log.info(REQUEST_MSG, PLANS_URL + PATH_ID, planId);
        var response = ResponseModel.of(planService.deletePlan(planId), OK);

        log.info(RESPONSE_MSG, PLANS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }
}
