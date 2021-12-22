package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.SERVICE_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.SERVICE_DELETE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.SERVICE_GET_ALL_SERVICES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.SERVICE_UPDATE_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.ServiceRqModel;
import com.budgetmanagementapp.model.home.ServiceRsModel;
import com.budgetmanagementapp.service.ServiceService;
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
    @PostMapping(SERVICE_CREATE_URL)
    public ResponseEntity<ResponseModel<ServiceRsModel>> createService(@RequestBody @Valid ServiceRqModel request) {

        log.info(REQUEST_MSG, SERVICE_CREATE_URL, request);
        var response = ResponseModel.of(serviceService.createService(request), CREATED);

        log.info(RESPONSE_MSG, SERVICE_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update service")
    @PostMapping(SERVICE_UPDATE_URL)
    public ResponseEntity<ResponseModel<ServiceRsModel>> updateService(
            @RequestBody @Valid ServiceRqModel request,
            @ApiParam(name = "service-id", type = "string", example = "", required = true)
            @RequestParam(name = "service-id") String serviceId) {

        log.info(REQUEST_MSG, SERVICE_UPDATE_URL, request);
        var response = ResponseModel.of(serviceService.updateService(request, serviceId), OK);

        log.info(RESPONSE_MSG, SERVICE_UPDATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete service")
    @PostMapping(SERVICE_DELETE_URL)
    public ResponseEntity<ResponseModel<ServiceRsModel>> deleteService(
            @ApiParam(name = "service-id", type = "string", example = "", required = true)
            @RequestParam(name = "service-id") String serviceId) {

        log.info(REQUEST_MSG, SERVICE_DELETE_URL, serviceId);
        var response = ResponseModel.of(serviceService.deleteService(serviceId), OK);

        log.info(RESPONSE_MSG, SERVICE_DELETE_URL, response);
        return ResponseEntity.ok(response);
    }

}
