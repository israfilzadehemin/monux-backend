package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.service.BannerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
public class BannerController {

    private final BannerService bannerService;

    @GetMapping(BANNER_GET_BANNER_BY_ID_URL)
    public ResponseEntity<?> getBannerById(@RequestParam("banner-id") String bannerId) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_GET_BANNER_BY_ID_URL, bannerId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.getBannerById(bannerId))
                        .build());
    }

    @GetMapping(BANNER_GET_BANNER_BY_KEYWORD_URL)
    public ResponseEntity<?> getBannerByKeyword(@RequestParam("banner-keyword") String keyword) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_GET_BANNER_BY_KEYWORD_URL, keyword));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.getBannerByKeyword(keyword))
                        .build());
    }

    @PostMapping(BANNER_CREATE_URL)
    public ResponseEntity<?> createBanner(@RequestBody BannerRqModel request) {

        log.info(format(REQUEST_MSG, BANNER_CREATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(bannerService.createBanner(request))
                        .build());
    }

    @PostMapping(BANNER_UPDATE_URL)
    public ResponseEntity<?> updateBanner(@RequestBody BannerRqModel request,
                                          @RequestParam(name = "banner-id") String bannerId) {

        log.info(format(REQUEST_MSG, BANNER_UPDATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.updateBanner(request, bannerId))
                        .build());
    }

    @PostMapping(BANNER_DELETE_URL)
    public ResponseEntity<?> deleteBanner(@RequestParam(name = "banner-id") String bannerId) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_DELETE_URL, bannerId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.deleteBanner(bannerId))
                        .build());
    }

}
