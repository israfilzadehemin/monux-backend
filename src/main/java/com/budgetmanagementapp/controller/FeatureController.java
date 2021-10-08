package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.service.FeatureService;
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
public class FeatureController {

    private final FeatureService featureService;

    @GetMapping(FEATURE_GET_ALL_FEATURES)
    public ResponseEntity<?> getAllFeatures(){

        log.info(format(REQUEST_MSG, PLAN_GET_ALL_PLANS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(featureService.getAllFeatures())
                        .build());
    }

    @PostMapping(FEATURE_ADD_FEATURE)
    public ResponseEntity<?> addFeature(@RequestBody FeatureRqModel request) {

        log.info(format(REQUEST_MSG, PLAN_ADD_PLAN_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(featureService.addFeature(request))
                        .build());
    }


}
