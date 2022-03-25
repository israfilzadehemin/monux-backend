package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static com.budgetmanagementapp.utility.UrlConstant.PLANS_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import com.budgetmanagementapp.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Tag(name = "Plan", description = "Plan operations")
@RequestMapping(PLANS_URL)
public class PlanController {

    private final PlanService planService;

    @Operation(description = "Get all plans")
    @GetMapping
    public ResponseEntity<ResponseModel<List<PlanRsModel>>> getAllPlans(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, PLANS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(planService.getAllPlans(language), OK);

        log.info(RESPONSE_MSG, PLANS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Add plan")
    @PostMapping
    public ResponseEntity<ResponseModel<PlanRsModel>> addPlan(@RequestBody @Valid PlanRqModel request) {

        log.info(REQUEST_MSG, PLANS_URL, request);
        var response = ResponseModel.of(planService.addPlan(request), CREATED);

        log.info(RESPONSE_MSG, PLANS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update plan")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<PlanRsModel>> updatePlan(
            @RequestBody @Valid PlanRqModel request,
            @PathVariable("id") String planId) {

        log.info(REQUEST_MSG, PLANS_URL + PATH_ID, request);
        var response = ResponseModel.of(planService.updatePlan(request, planId), OK);

        log.info(REQUEST_MSG, PLANS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Delete plan")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<PlanRsModel>> deletePlan(
            @PathVariable("id") String planId) {

        log.info(REQUEST_MSG, PLANS_URL + PATH_ID, planId);
        var response = ResponseModel.of(planService.deletePlan(planId), OK);

        log.info(RESPONSE_MSG, PLANS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }
}
