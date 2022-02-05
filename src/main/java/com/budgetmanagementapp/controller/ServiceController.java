package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.service.ServiceRqModel;
import com.budgetmanagementapp.model.service.ServiceRsModel;
import com.budgetmanagementapp.model.service.UpdateServiceRqModel;
import com.budgetmanagementapp.service.ServiceService;
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
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Service")
public class ServiceController {

    private final ServiceService serviceService;

    @ApiOperation("Get all steps")
    @GetMapping(SERVICE_GET_ALL_SERVICES_URL)
    public ResponseEntity<ResponseModel<List<ServiceRsModel>>> getAllSteps(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, SERVICE_GET_ALL_SERVICES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(serviceService.getAllServices(language), OK);

        log.info(RESPONSE_MSG, SERVICE_GET_ALL_SERVICES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create service")
    @PostMapping(SERVICE_URL)
    public ResponseEntity<ResponseModel<ServiceRsModel>> createService(@RequestBody @Valid ServiceRqModel request) {

        log.info(REQUEST_MSG, SERVICE_URL, request);
        var response = ResponseModel.of(serviceService.createService(request), CREATED);

        log.info(RESPONSE_MSG, SERVICE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update service")
    @PutMapping(SERVICE_URL)
    public ResponseEntity<ResponseModel<ServiceRsModel>> updateService(@RequestBody @Valid UpdateServiceRqModel request) {

        log.info(REQUEST_MSG, SERVICE_URL, request);
        var response = ResponseModel.of(serviceService.updateService(request), OK);

        log.info(RESPONSE_MSG, SERVICE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete service")
    @DeleteMapping(SERVICE_URL)
    public ResponseEntity<ResponseModel<ServiceRsModel>> deleteService(
            @ApiParam(name = "service-id", type = "string", required = true)
            @RequestParam(name = "service-id") String serviceId) {

        log.info(REQUEST_MSG, SERVICE_URL, serviceId);
        var response = ResponseModel.of(serviceService.deleteService(serviceId), OK);

        log.info(RESPONSE_MSG, SERVICE_URL, response);
        return ResponseEntity.ok(response);
    }

}
