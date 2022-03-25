package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.FEATURES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import com.budgetmanagementapp.service.FeatureService;
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
@Tag(name = "Feature", description = "Feature operations")
@RequestMapping(FEATURES_URL)
public class FeatureController {

    private final FeatureService featureService;

    @Operation(description = "Get all features")
    @GetMapping
    public ResponseEntity<ResponseModel<List<FeatureRsModel>>> getAllFeatures(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, FEATURES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(featureService.getAllFeatures(language), OK);

        log.info(RESPONSE_MSG, FEATURES_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Add feature")
    @PostMapping
    public ResponseEntity<ResponseModel<FeatureRsModel>> addFeature(@RequestBody @Valid FeatureRqModel request) {

        log.info(REQUEST_MSG, FEATURES_URL, request);
        var response = ResponseModel.of(featureService.addFeature(request), CREATED);

        log.info(RESPONSE_MSG, FEATURES_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update feature")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FeatureRsModel>> updateFeature(
            @RequestBody @Valid FeatureRqModel request,
            @PathVariable("id") String featureId) {

        log.info(REQUEST_MSG, FEATURES_URL + PATH_ID, request);
        var response = ResponseModel.of(featureService.updateFeature(request, featureId), OK);

        log.info(RESPONSE_MSG, FEATURES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Delete feature")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<FeatureRsModel>> deleteFeature(
            @PathVariable(name = "id") String featureId) {

        log.info(RESPONSE_MSG, FEATURES_URL + PATH_ID, featureId);
        var response = ResponseModel.of(featureService.deleteFeature(featureId), OK);

        log.info(RESPONSE_MSG, FEATURES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }


}
