package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.service.FagService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.FAG_GET_ALL_FAGS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.FAG_GET_FAG_BY_ID;
import static java.lang.String.format;

@RestController
@AllArgsConstructor
@Log4j2
public class FagController {

    private final FagService fagService;

    @GetMapping(FAG_GET_ALL_FAGS_URL)
    public ResponseEntity<?> getAllFags() {
        log.info(format(REQUEST_MSG, FAG_GET_ALL_FAGS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(fagService.getAllFags())
                        .build());
    }

    @GetMapping(FAG_GET_FAG_BY_ID)
    public ResponseEntity<?> getFagById(@RequestParam(name = "fag-id") String fagId) {
        log.info(format(REQUEST_MSG, FAG_GET_FAG_BY_ID, fagId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(fagService.getFagById(fagId))
                        .build());
    }
}
