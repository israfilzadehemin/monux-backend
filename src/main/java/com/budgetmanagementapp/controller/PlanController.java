package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.UpdatePlanRqModel;
import com.budgetmanagementapp.service.PlanService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
public class PlanController {

    private final PlanService planService;

    @GetMapping(PLAN_GET_ALL_PLANS_URL)
    public ResponseEntity<?> getAllPlans() {
        log.info(format(REQUEST_MSG, PLAN_GET_ALL_PLANS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(planService.getAllPlans())
                        .build());
    }

    @PostMapping(PLAN_ADD_PLAN_URL)
    public ResponseEntity<?> addPlan(@RequestBody PlanRqModel request) {

        log.info(format(REQUEST_MSG, PLAN_ADD_PLAN_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(planService.addPlan(request))
                        .build());
    }

    @PostMapping(PLAN_UPDATE_PLAN_URL)
    public ResponseEntity<?> updatePlan(@RequestBody UpdatePlanRqModel request) {

        log.info(format(REQUEST_MSG, PLAN_UPDATE_PLAN_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(planService.updatePlan(request))
                        .build());
    }

    @PostMapping(PLAN_DELETE_PLAN_URL)
    public ResponseEntity<?> deletePlan(@RequestParam("plan-id") String planId) {
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(planService.deletePlan(planId))
                        .build());
    }
}