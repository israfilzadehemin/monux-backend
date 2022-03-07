package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.service.ServiceRqModel;
import com.budgetmanagementapp.model.service.ServiceRsModel;
import com.budgetmanagementapp.service.ServiceService;
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
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static com.budgetmanagementapp.utility.UrlConstant.SERVICES_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Service")
@RequestMapping(SERVICES_URL)
public class ServiceController {

    private final ServiceService serviceService;

    @ApiOperation("Get all steps")
    @GetMapping
    public ResponseEntity<ResponseModel<List<ServiceRsModel>>> getAllSteps(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, SERVICES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(serviceService.getAllServices(language), OK);

        log.info(RESPONSE_MSG, SERVICES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create service")
    @PostMapping
    public ResponseEntity<ResponseModel<ServiceRsModel>> createService(@RequestBody @Valid ServiceRqModel request) {

        log.info(REQUEST_MSG, SERVICES_URL, request);
        var response = ResponseModel.of(serviceService.createService(request), CREATED);

        log.info(RESPONSE_MSG, SERVICES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update service")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<ServiceRsModel>> updateService(
            @RequestBody @Valid ServiceRqModel request,
            @PathVariable("id") String serviceId) {

        log.info(REQUEST_MSG, SERVICES_URL + PATH_ID, request);
        var response = ResponseModel.of(serviceService.updateService(request, serviceId), OK);

        log.info(RESPONSE_MSG, SERVICES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete service")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<ServiceRsModel>> deleteService(
            @PathVariable(name = "id") String serviceId) {

        log.info(REQUEST_MSG, SERVICES_URL + PATH_ID, serviceId);
        var response = ResponseModel.of(serviceService.deleteService(serviceId), OK);

        log.info(RESPONSE_MSG, SERVICES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

}
