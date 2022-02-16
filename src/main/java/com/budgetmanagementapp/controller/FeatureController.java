package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import com.budgetmanagementapp.service.FeatureService;
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
import static com.budgetmanagementapp.utility.UrlConstant.FEATURES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Feature")
@RequestMapping(FEATURES_URL)
public class FeatureController {

    private final FeatureService featureService;

    @ApiOperation("Get all features")
    @GetMapping
    public ResponseEntity<ResponseModel<List<FeatureRsModel>>> getAllFeatures(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, FEATURES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(featureService.getAllFeatures(language), OK);

        log.info(RESPONSE_MSG, FEATURES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Add feature")
    @PostMapping
    public ResponseEntity<ResponseModel<FeatureRsModel>> addFeature(@RequestBody @Valid FeatureRqModel request) {

        log.info(REQUEST_MSG, FEATURES_URL, request);
        var response = ResponseModel.of(featureService.addFeature(request), CREATED);

        log.info(RESPONSE_MSG, FEATURES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update feature")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FeatureRsModel>> updateFeature(
            @RequestBody @Valid FeatureRqModel request,
            @PathVariable("id") String featureId) {

        log.info(REQUEST_MSG, FEATURES_URL + PATH_ID, request);
        var response = ResponseModel.of(featureService.updateFeature(request, featureId), OK);

        log.info(RESPONSE_MSG, FEATURES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete feature")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FeatureRsModel>> deleteFeature(
            @ApiParam(name = "feature-id", type = "string", required = true)
            @PathVariable(name = "id") String featureId) {

        log.info(RESPONSE_MSG, FEATURES_URL + PATH_ID, featureId);
        var response = ResponseModel.of(featureService.deleteFeature(featureId), OK);

        log.info(RESPONSE_MSG, FEATURES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }


}
