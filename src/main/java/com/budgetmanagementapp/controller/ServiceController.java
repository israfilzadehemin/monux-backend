package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static com.budgetmanagementapp.utility.UrlConstant.SERVICES_URL;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.service.ServiceRqModel;
import com.budgetmanagementapp.model.service.ServiceRsModel;
import com.budgetmanagementapp.service.ServiceService;
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
@Tag(name = "Service", description = "Service operations")
@RequestMapping(SERVICES_URL)
public class ServiceController {

    private final ServiceService serviceService;

    @Operation(description = "Get all steps")
    @GetMapping
    public ResponseEntity<ResponseModel<List<ServiceRsModel>>> getAllSteps(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, SERVICES_URL, NO_BODY_MSG);
        var response = ResponseModel.of(serviceService.getAllServices(language), OK);

        log.info(RESPONSE_MSG, SERVICES_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Create service")
    @PostMapping
    public ResponseEntity<ResponseModel<ServiceRsModel>> createService(@RequestBody @Valid ServiceRqModel request) {

        log.info(REQUEST_MSG, SERVICES_URL, request);
        var response = ResponseModel.of(serviceService.createService(request), CREATED);

        log.info(RESPONSE_MSG, SERVICES_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update service")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<ServiceRsModel>> updateService(
            @RequestBody @Valid ServiceRqModel request,
            @PathVariable("id") String serviceId) {

        log.info(REQUEST_MSG, SERVICES_URL + PATH_ID, request);
        var response = ResponseModel.of(serviceService.updateService(request, serviceId), OK);

        log.info(RESPONSE_MSG, SERVICES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Delete service")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<ServiceRsModel>> deleteService(
            @PathVariable(name = "id") String serviceId) {

        log.info(REQUEST_MSG, SERVICES_URL + PATH_ID, serviceId);
        var response = ResponseModel.of(serviceService.deleteService(serviceId), OK);

        log.info(RESPONSE_MSG, SERVICES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

}
