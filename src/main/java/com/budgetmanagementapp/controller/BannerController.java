package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.service.BannerService;
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
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Banner")
public class BannerController {

    private final BannerService bannerService;

    @ApiOperation("Get banner by id")
    @GetMapping(BANNER_GET_BANNER_BY_ID_URL)
    public ResponseEntity<?> getBannerById(
            @ApiParam(
                    name = "banner-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam("banner-id") String bannerId) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_GET_BANNER_BY_ID_URL, bannerId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.getBannerById(bannerId))
                        .build());
    }

    @ApiOperation("Get banner by keyword")
    @GetMapping(BANNER_GET_BANNER_BY_KEYWORD_URL)
    public ResponseEntity<?> getBannerByKeyword(
            @ApiParam(
                    name = "banner-keyword",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam("banner-keyword") String keyword) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_GET_BANNER_BY_KEYWORD_URL, keyword));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.getBannerByKeyword(keyword))
                        .build());
    }

    @ApiOperation("Create banner")
    @PostMapping(BANNER_CREATE_URL)
    public ResponseEntity<?> createBanner(@RequestBody @Valid BannerRqModel request) {

        log.info(format(REQUEST_MSG, BANNER_CREATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(bannerService.createBanner(request))
                        .build());
    }

    @ApiOperation("Update banner")
    @PostMapping(BANNER_UPDATE_URL)
    public ResponseEntity<?> updateBanner(@RequestBody @Valid BannerRqModel request,
                                          @ApiParam(
                                                  name = "banner-id",
                                                  type = "string",
                                                  example = "",
                                                  required = true)
                                          @RequestParam(name = "banner-id") String bannerId) {

        log.info(format(REQUEST_MSG, BANNER_UPDATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.updateBanner(request, bannerId))
                        .build());
    }

    @ApiOperation("Delete banner")
    @PostMapping(BANNER_DELETE_URL)
    public ResponseEntity<?> deleteBanner(
            @ApiParam(
                    name = "banner-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "banner-id") String bannerId) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_DELETE_URL, bannerId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(bannerService.deleteBanner(bannerId))
                        .build());
    }

}
