package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import com.budgetmanagementapp.model.plan.UpdatePlanRqModel;
import com.budgetmanagementapp.service.PlanService;
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

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Plan")
public class PlanController {

    private final PlanService planService;

    @ApiOperation("Get all plans")
    @GetMapping(PLAN_GET_ALL_PLANS_URL)
    public ResponseEntity<ResponseModel<List<PlanRsModel>>> getAllPlans(
            @ApiParam(
                    name = "language",
                    type = "string",
                    example = "en, az, ru",
                    required = true)
            @RequestParam(name = "language") String language
    ) {
        log.info(format(REQUEST_MSG, PLAN_GET_ALL_PLANS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(planService.getAllPlans(language), HttpStatus.OK));
    }

    @ApiOperation("Add plan")
    @PostMapping(PLAN_ADD_PLAN_URL)
    public ResponseEntity<ResponseModel<PlanRsModel>> addPlan(@RequestBody @Valid PlanRqModel request) {

        log.info(format(REQUEST_MSG, PLAN_ADD_PLAN_URL, request));
        return ResponseEntity.ok(
                ResponseModel.of(planService.addPlan(request), HttpStatus.CREATED));
    }

    @ApiOperation("Update plan")
    @PostMapping(PLAN_UPDATE_PLAN_URL)
    public ResponseEntity<ResponseModel<PlanRsModel>> updatePlan(@RequestBody @Valid UpdatePlanRqModel request) {

        log.info(format(REQUEST_MSG, PLAN_UPDATE_PLAN_URL, request));
        return ResponseEntity.ok(
                ResponseModel.of(planService.updatePlan(request), HttpStatus.OK));
    }

    @ApiOperation("Delete plan")
    @PostMapping(PLAN_DELETE_PLAN_URL)
    public ResponseEntity<ResponseModel<PlanRsModel>> deletePlan(
            @ApiParam(
                    name = "plan-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam("plan-id") String planId) {
        return ResponseEntity.ok(
                ResponseModel.of(planService.deletePlan(planId), HttpStatus.OK));
    }
}
