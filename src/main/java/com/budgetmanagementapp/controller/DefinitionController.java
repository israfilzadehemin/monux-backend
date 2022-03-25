package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.DEFINITIONS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import com.budgetmanagementapp.service.DefinitionService;
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
@Tag(name = "Definition", description = "Definition operations")
@RequestMapping(DEFINITIONS_URL)
public class DefinitionController {

    private final DefinitionService definitionService;

    @Operation(description = "Get all definitions")
    @GetMapping
    public ResponseEntity<ResponseModel<List<DefinitionRsModel>>> getAllDefinitions(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, DEFINITIONS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(definitionService.getAllDefinitions(language), OK);

        log.info(RESPONSE_MSG, DEFINITIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Create definition")
    @PostMapping
    public ResponseEntity<ResponseModel<DefinitionRsModel>> createDefinition(
            @RequestBody @Valid DefinitionRqModel requestBody) {

        log.info(REQUEST_MSG, DEFINITIONS_URL, requestBody);
        var response = ResponseModel.of(definitionService.createDefinition(requestBody), CREATED);

        log.info(RESPONSE_MSG, DEFINITIONS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update definition")
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

    @Operation(description = "Delete definition")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<DefinitionRsModel>> deleteDefinition(
            @PathVariable(name = "id") String definitionId) {

        log.info(REQUEST_MSG, DEFINITIONS_URL + PATH_ID, definitionId);
        var response = ResponseModel.of(definitionService.deleteDefinition(definitionId), OK);

        log.info(RESPONSE_MSG, DEFINITIONS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }
}
