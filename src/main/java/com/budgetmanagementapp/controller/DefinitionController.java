package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import com.budgetmanagementapp.model.definition.UpdateDefinitionRqModel;
import com.budgetmanagementapp.service.DefinitionService;
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
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Definition")
public class DefinitionController {

    private final DefinitionService definitionService;

    @ApiOperation("Get all definitions")
    @GetMapping(DEFINITION_GET_ALL_DEFINITIONS_URL)
    public ResponseEntity<ResponseModel<List<DefinitionRsModel>>> getAllDefinitions() {

        log.info(format(REQUEST_MSG, DEFINITION_GET_ALL_DEFINITIONS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(definitionService.getAllDefinitions(), HttpStatus.OK));
    }

    @ApiOperation("Create definition")
    @PostMapping(DEFINITION_CREATE_URL)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> createDefinition(@RequestBody @Valid DefinitionRqModel requestBody) {

        log.info(format(REQUEST_MSG, DEFINITION_CREATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(definitionService.createDefinition(requestBody), HttpStatus.CREATED));
    }

    @ApiOperation("Update definition")
    @PostMapping(DEFINITION_UPDATE_URL)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> updateDefinition(@RequestBody @Valid UpdateDefinitionRqModel requestBody) {

        log.info(format(REQUEST_MSG, DEFINITION_UPDATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(definitionService.updateDefinition(requestBody), HttpStatus.OK));
    }

    @ApiOperation("Delete definition")
    @PostMapping(DEFINITION_DELETE_URL)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> deleteDefinition(
            @ApiParam(
                    name = "definition-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "definition-id") String definitionId) {

        log.info(format(REQUEST_PARAM_MSG, DEFINITION_DELETE_URL, definitionId));
        return ResponseEntity.ok(
                ResponseModel.of(definitionService.deleteDefinition(definitionId), HttpStatus.OK));
    }
}
