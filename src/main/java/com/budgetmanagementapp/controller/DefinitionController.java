package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import com.budgetmanagementapp.service.DefinitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.DEFINITIONS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Definition")
@RequestMapping(DEFINITIONS_URL)
public class DefinitionController {

    private final DefinitionService definitionService;

    @ApiOperation("Get all definitions")
    @GetMapping
    public ResponseEntity<ResponseModel<List<DefinitionRsModel>>> getAllDefinitions(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, DEFINITIONS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(definitionService.getAllDefinitions(language), OK);

        log.info(RESPONSE_MSG, DEFINITIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create definition")
    @PostMapping
    public ResponseEntity<ResponseModel<DefinitionRsModel>> createDefinition(
            @RequestBody @Valid DefinitionRqModel requestBody) {

        log.info(REQUEST_MSG, DEFINITIONS_URL, requestBody);
        var response = ResponseModel.of(definitionService.createDefinition(requestBody), CREATED);

        log.info(RESPONSE_MSG, DEFINITIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update definition")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> updateDefinition(
            @RequestBody @Valid DefinitionRqModel requestBody,
            @PathVariable("id") String definitionId) {

        log.info(REQUEST_MSG, DEFINITIONS_URL + PATH_ID, requestBody);
        var response = ResponseModel.of(
                definitionService.updateDefinition(requestBody, definitionId), OK);

        log.info(RESPONSE_MSG, DEFINITIONS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete definition")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> deleteDefinition(
            @PathVariable(name = "id") String definitionId) {

        log.info(REQUEST_MSG, DEFINITIONS_URL + PATH_ID, definitionId);
        var response = ResponseModel.of(definitionService.deleteDefinition(definitionId), OK);

        log.info(RESPONSE_MSG, DEFINITIONS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }
}
