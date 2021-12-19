package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.DEFINITION_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.DEFINITION_DELETE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.DEFINITION_GET_ALL_DEFINITIONS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.DEFINITION_UPDATE_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import com.budgetmanagementapp.model.definition.UpdateDefinitionRqModel;
import com.budgetmanagementapp.service.DefinitionService;
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
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Definition")
public class DefinitionController {

    private final DefinitionService definitionService;

    @ApiOperation("Get all definitions")
    @GetMapping(DEFINITION_GET_ALL_DEFINITIONS_URL)
    public ResponseEntity<ResponseModel<List<DefinitionRsModel>>> getAllDefinitions(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, DEFINITION_GET_ALL_DEFINITIONS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(definitionService.getAllDefinitions(language), OK);

        log.info(RESPONSE_MSG, DEFINITION_GET_ALL_DEFINITIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create definition")
    @PostMapping(DEFINITION_CREATE_URL)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> createDefinition(
            @RequestBody @Valid DefinitionRqModel requestBody) {

        log.info(REQUEST_MSG, DEFINITION_CREATE_URL, requestBody);
        var response = ResponseModel.of(definitionService.createDefinition(requestBody), CREATED);

        log.info(RESPONSE_MSG, DEFINITION_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update definition")
    @PostMapping(DEFINITION_UPDATE_URL)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> updateDefinition(
            @RequestBody @Valid UpdateDefinitionRqModel requestBody) {

        log.info(REQUEST_MSG, DEFINITION_UPDATE_URL, requestBody);
        var response = ResponseModel.of(definitionService.updateDefinition(requestBody), OK);

        log.info(RESPONSE_MSG, DEFINITION_UPDATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete definition")
    @PostMapping(DEFINITION_DELETE_URL)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> deleteDefinition(
            @ApiParam(name = "definition-id", type = "string", example = "", required = true)
            @RequestParam(name = "definition-id") String definitionId) {

        log.info(REQUEST_MSG, DEFINITION_DELETE_URL, definitionId);
        var response = ResponseModel.of(definitionService.deleteDefinition(definitionId), OK);

        log.info(RESPONSE_MSG, DEFINITION_DELETE_URL, response);
        return ResponseEntity.ok(response);
    }
}
