package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import com.budgetmanagementapp.model.feature.UpdateFeatureRqModel;
import com.budgetmanagementapp.service.FeatureService;
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

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Feature")
public class FeatureController {

    private final FeatureService featureService;

    @ApiOperation("Get all features")
    @GetMapping(FEATURE_GET_ALL_FEATURES)
    public ResponseEntity<ResponseModel<List<FeatureRsModel>>> getAllFeatures(){

        log.info(format(REQUEST_MSG, FEATURE_GET_ALL_FEATURES, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(featureService.getAllFeatures(), HttpStatus.OK));
    }

    @ApiOperation("Add feature")
    @PostMapping(FEATURE_ADD_FEATURE)
    public ResponseEntity<ResponseModel<FeatureRsModel>> addFeature(@RequestBody @Valid FeatureRqModel request) {

        log.info(format(REQUEST_MSG, FEATURE_ADD_FEATURE, request));
        return ResponseEntity.ok(
                ResponseModel.of(featureService.addFeature(request), HttpStatus.CREATED));
    }

    @ApiOperation("Update feature")
    @PostMapping(FEATURE_UPDATE_FEATURE)
    public ResponseEntity<ResponseModel<FeatureRsModel>> updateFeature(@RequestBody @Valid UpdateFeatureRqModel request) {

        log.info(format(REQUEST_MSG, FEATURE_UPDATE_FEATURE, request));
        return ResponseEntity.ok(
                ResponseModel.of(featureService.updateFeature(request), HttpStatus.OK));
    }

    @ApiOperation("Delete feature")
    @PostMapping(FEATURE_DELETE_FEATURE)
    public ResponseEntity<ResponseModel<FeatureRsModel>> deleteFeature(
            @ApiParam(
                    name = "feature-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "feature-id") String featureId) {

        log.info(format(REQUEST_PARAM_MSG, FEATURE_DELETE_FEATURE, featureId));
        return ResponseEntity.ok(
                ResponseModel.of(featureService.deleteFeature(featureId), HttpStatus.OK));
    }


}
