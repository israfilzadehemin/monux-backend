package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.service.DefinitionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.DEFINITION_GET_ALL_DEFINITIONS_URL;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
public class DefinitionController {

    private final DefinitionService definitionService;

    @GetMapping(DEFINITION_GET_ALL_DEFINITIONS_URL)
    public ResponseEntity<?> getAllDefinitions() {

        log.info(format(REQUEST_MSG, DEFINITION_GET_ALL_DEFINITIONS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(definitionService.getAllDefinitions())
                        .build());
    }
}
