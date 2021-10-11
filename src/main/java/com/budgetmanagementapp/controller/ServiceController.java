package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.service.ServiceService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.SERVICE_GET_ALL_SERVICES_URL;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping(SERVICE_GET_ALL_SERVICES_URL)
    public ResponseEntity<?> getAllSteps() {

        log.info(format(REQUEST_MSG, SERVICE_GET_ALL_SERVICES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(serviceService.getAllServices())
                        .build());
    }
}
