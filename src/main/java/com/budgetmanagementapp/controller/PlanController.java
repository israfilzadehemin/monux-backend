package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.PLAN_ADD_PLAN_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PLAN_DELETE_PLAN_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PLAN_GET_ALL_PLANS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PLAN_UPDATE_PLAN_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import com.budgetmanagementapp.model.plan.UpdatePlanRqModel;
import com.budgetmanagementapp.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Plan")
public class PlanController {

    private final PlanService planService;

    @ApiOperation("Get all plans")
    @GetMapping(PLAN_GET_ALL_PLANS_URL)
    public ResponseEntity<ResponseModel<List<PlanRsModel>>> getAllPlans(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, PLAN_GET_ALL_PLANS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(planService.getAllPlans(language), OK);

        log.info(RESPONSE_MSG, PLAN_GET_ALL_PLANS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Add plan")
    @PostMapping(PLAN_ADD_PLAN_URL)
    public ResponseEntity<ResponseModel<PlanRsModel>> addPlan(@RequestBody @Valid PlanRqModel request) {

        log.info(REQUEST_MSG, PLAN_ADD_PLAN_URL, request);
        var response = ResponseModel.of(planService.addPlan(request), CREATED);

        log.info(RESPONSE_MSG, PLAN_ADD_PLAN_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update plan")
    @PostMapping(PLAN_UPDATE_PLAN_URL)
    public ResponseEntity<ResponseModel<PlanRsModel>> updatePlan(@RequestBody @Valid UpdatePlanRqModel request) {

        log.info(REQUEST_MSG, PLAN_UPDATE_PLAN_URL, request);
        var response = ResponseModel.of(planService.updatePlan(request), OK);

        log.info(REQUEST_MSG, PLAN_UPDATE_PLAN_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete plan")
    @PostMapping(PLAN_DELETE_PLAN_URL)
    public ResponseEntity<ResponseModel<PlanRsModel>> deletePlan(
            @ApiParam(name = "plan-id", type = "string", example = "", required = true)
            @RequestParam("plan-id") String planId) {

        log.info(REQUEST_MSG, PLAN_DELETE_PLAN_URL, planId);
        var response = ResponseModel.of(planService.deletePlan(planId), OK);

        log.info(RESPONSE_MSG, PLAN_DELETE_PLAN_URL, response);
        return ResponseEntity.ok(response);
    }
}
