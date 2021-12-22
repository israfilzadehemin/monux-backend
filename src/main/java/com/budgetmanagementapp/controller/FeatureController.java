package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.FEATURE_ADD_FEATURE;
import static com.budgetmanagementapp.utility.UrlConstant.FEATURE_DELETE_FEATURE;
import static com.budgetmanagementapp.utility.UrlConstant.FEATURE_GET_ALL_FEATURES;
import static com.budgetmanagementapp.utility.UrlConstant.FEATURE_UPDATE_FEATURE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import com.budgetmanagementapp.model.feature.UpdateFeatureRqModel;
import com.budgetmanagementapp.service.FeatureService;
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
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Feature")
public class FeatureController {

    private final FeatureService featureService;

    @ApiOperation("Get all features")
    @GetMapping(FEATURE_GET_ALL_FEATURES)
    public ResponseEntity<ResponseModel<List<FeatureRsModel>>> getAllFeatures(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, FEATURE_GET_ALL_FEATURES, NO_BODY_MSG);
        var response = ResponseModel.of(featureService.getAllFeatures(language), OK);

        log.info(RESPONSE_MSG, FEATURE_GET_ALL_FEATURES, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Add feature")
    @PostMapping(FEATURE_ADD_FEATURE)
    public ResponseEntity<ResponseModel<FeatureRsModel>> addFeature(@RequestBody @Valid FeatureRqModel request) {

        log.info(REQUEST_MSG, FEATURE_ADD_FEATURE, request);
        var response = ResponseModel.of(featureService.addFeature(request), CREATED);

        log.info(RESPONSE_MSG, FEATURE_ADD_FEATURE, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update feature")
    @PostMapping(FEATURE_UPDATE_FEATURE)
    public ResponseEntity<ResponseModel<FeatureRsModel>> updateFeature(
            @RequestBody @Valid UpdateFeatureRqModel request) {

        log.info(REQUEST_MSG, FEATURE_UPDATE_FEATURE, request);
        var response = ResponseModel.of(featureService.updateFeature(request), OK);

        log.info(RESPONSE_MSG, FEATURE_UPDATE_FEATURE, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete feature")
    @PostMapping(FEATURE_DELETE_FEATURE)
    public ResponseEntity<ResponseModel<FeatureRsModel>> deleteFeature(
            @ApiParam(name = "feature-id", type = "string", example = "", required = true)
            @RequestParam(name = "feature-id") String featureId) {

        log.info(RESPONSE_MSG, FEATURE_DELETE_FEATURE, featureId);
        var response = ResponseModel.of(featureService.deleteFeature(featureId), OK);

        log.info(RESPONSE_MSG, FEATURE_DELETE_FEATURE, response);
        return ResponseEntity.ok(response);
    }


}
