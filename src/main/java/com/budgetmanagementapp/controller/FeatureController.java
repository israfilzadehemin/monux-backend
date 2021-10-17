package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.UpdateFeatureRqModel;
import com.budgetmanagementapp.service.FeatureService;
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
public class FeatureController {

    private final FeatureService featureService;

    @GetMapping(FEATURE_GET_ALL_FEATURES)
    public ResponseEntity<?> getAllFeatures(){

        log.info(format(REQUEST_MSG, FEATURE_GET_ALL_FEATURES, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(featureService.getAllFeatures())
                        .build());
    }

    @PostMapping(FEATURE_ADD_FEATURE)
    public ResponseEntity<?> addFeature(@RequestBody FeatureRqModel request) {

        log.info(format(REQUEST_MSG, FEATURE_ADD_FEATURE, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(featureService.addFeature(request))
                        .build());
    }

    @PostMapping(FEATURE_UPDATE_FEATURE)
    public ResponseEntity<?> updateFeature(@RequestBody UpdateFeatureRqModel request) {

        log.info(format(REQUEST_MSG, FEATURE_UPDATE_FEATURE, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(featureService.updateFeature(request))
                        .build());
    }
    @PostMapping(FEATURE_DELETE_FEATURE)
    public ResponseEntity<?> deleteFeature(@RequestParam(name = "feature-id") String featureId) {

        log.info(format(REQUEST_PARAM_MSG, FEATURE_DELETE_FEATURE, featureId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(featureService.deleteFeature(featureId))
                        .build());
    }


}
