package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.model.home.BannerRsModel;
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
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerById(
            @ApiParam(
                    name = "banner-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam("banner-id") String bannerId,
            @ApiParam(
                    name = "language",
                    type = "string",
                    example = "az, en, ru",
                    required = true)
            @RequestParam("language") String language) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_GET_BANNER_BY_ID_URL, bannerId));
        return ResponseEntity.ok(
                ResponseModel.of(bannerService.getBannerById(bannerId, language), HttpStatus.OK));
    }

    @ApiOperation("Get banner by keyword")
    @GetMapping(BANNER_GET_BANNER_BY_KEYWORD_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerByKeyword(
            @ApiParam(
                    name = "banner-keyword",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam("banner-keyword") String keyword,
            @ApiParam(
                    name = "language",
                    type = "string",
                    example = "az, en, ru",
                    required = true)
            @RequestParam("language") String language) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_GET_BANNER_BY_KEYWORD_URL, keyword));
        return ResponseEntity.ok(
                ResponseModel.of(bannerService.getBannerByKeyword(keyword, language),HttpStatus.OK));
    }

    @ApiOperation("Create banner")
    @PostMapping(BANNER_CREATE_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> createBanner(@RequestBody @Valid BannerRqModel request) {

        log.info(format(REQUEST_MSG, BANNER_CREATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.of(bannerService.createBanner(request), HttpStatus.CREATED));
    }

    @ApiOperation("Update banner")
    @PostMapping(BANNER_UPDATE_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> updateBanner(@RequestBody @Valid BannerRqModel request,
                                          @ApiParam(
                                                  name = "banner-id",
                                                  type = "string",
                                                  example = "",
                                                  required = true)
                                          @RequestParam(name = "banner-id") String bannerId) {

        log.info(format(REQUEST_MSG, BANNER_UPDATE_URL, request));
        return ResponseEntity.ok(
                ResponseModel.of(bannerService.updateBanner(request, bannerId), HttpStatus.OK));
    }

    @ApiOperation("Delete banner")
    @PostMapping(BANNER_DELETE_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> deleteBanner(
            @ApiParam(
                    name = "banner-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "banner-id") String bannerId) {

        log.info(format(REQUEST_PARAM_MSG, BANNER_DELETE_URL, bannerId));
        return ResponseEntity.ok(
                ResponseModel.of(bannerService.deleteBanner(bannerId), HttpStatus.OK));

    }
}
