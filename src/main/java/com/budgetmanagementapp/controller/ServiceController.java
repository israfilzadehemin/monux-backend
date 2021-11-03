package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.ServiceRqModel;
import com.budgetmanagementapp.model.home.StepRqModel;
import com.budgetmanagementapp.service.ServiceService;
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

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.STEP_DELETE_URL;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Service")
public class ServiceController {

    private final ServiceService serviceService;

    @ApiOperation("Get all steps")
    @GetMapping(SERVICE_GET_ALL_SERVICES_URL)
    public ResponseEntity<?> getAllSteps() {

        log.info(format(REQUEST_MSG, SERVICE_GET_ALL_SERVICES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(serviceService.getAllServices())
                        .build());
    }

    @ApiOperation("Create service")
    @PostMapping(SERVICE_CREATE_URL)
    public ResponseEntity<?> createService(@RequestBody @Valid ServiceRqModel request) {

        log.info(format(REQUEST_MSG, SERVICE_CREATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(serviceService.createService(request))
                        .build());
    }

    @ApiOperation("Update service")
    @PostMapping(SERVICE_UPDATE_URL)
    public ResponseEntity<?> updateService(@RequestBody @Valid ServiceRqModel request,
                                           @ApiParam(
                                                   name = "service-id",
                                                   type = "string",
                                                   example = "",
                                                   required = true)
                                           @RequestParam(name = "service-id") String serviceId) {

        log.info(format(REQUEST_MSG, SERVICE_UPDATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(serviceService.updateService(request, serviceId))
                        .build());
    }

    @ApiOperation("Delete service")
    @PostMapping(SERVICE_DELETE_URL)
    public ResponseEntity<?> deleteService(
            @ApiParam(
                    name = "service-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "service-id") String serviceId) {

        log.info(format(REQUEST_PARAM_MSG, SERVICE_DELETE_URL, serviceId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(serviceService.deleteService(serviceId))
                        .build());
    }

}
