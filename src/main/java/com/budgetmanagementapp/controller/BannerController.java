package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.banner.BannerRqModel;
import com.budgetmanagementapp.model.banner.BannerRsModel;
import com.budgetmanagementapp.model.banner.UpdateBannerRqModel;
import com.budgetmanagementapp.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Banner")
public class BannerController {

    private final BannerService bannerService;

    @ApiOperation("Get banner by id")
    @GetMapping(BANNER_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerById(
            @ApiParam(name = "banner-id", type = "string", required = true)
            @RequestParam("banner-id") String bannerId,
            @ApiParam(name = "language", type = "string", example = "az, en, ru", required = true)
            @RequestParam("language") String language) {

        log.info(REQUEST_MSG, BANNER_URL, bannerId);
        var response = ResponseModel.of(bannerService.getBannerById(bannerId, language), OK);

        log.info(RESPONSE_MSG, BANNER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get banner by keyword")
    @GetMapping(BANNER_BY_KEYWORD_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerByKeyword(
            @ApiParam(name = "banner-keyword", type = "string", required = true)
            @RequestParam("banner-keyword") String keyword,
            @ApiParam(name = "language", type = "string", example = "az, en, ru", required = true)
            @RequestParam("language") String language) {

        log.info(REQUEST_MSG, BANNER_BY_KEYWORD_URL, keyword);
        var response = ResponseModel.of(bannerService.getBannerByKeyword(keyword, language), OK);

        log.info(RESPONSE_MSG, BANNER_BY_KEYWORD_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create banner")
    @PostMapping(BANNER_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> createBanner(@RequestBody @Valid BannerRqModel request) {

        log.info(REQUEST_MSG, BANNER_URL, request);
        var response = ResponseModel.of(bannerService.createBanner(request), CREATED);

        log.info(RESPONSE_MSG, BANNER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update banner")
    @PutMapping(BANNER_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> updateBanner(
            @RequestBody @Valid UpdateBannerRqModel request) {

        log.info(REQUEST_MSG, BANNER_URL, request);
        var response = ResponseModel.of(bannerService.updateBanner(request), OK);

        log.info(RESPONSE_MSG, BANNER_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete banner")
    @DeleteMapping(BANNER_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> deleteBanner(
            @ApiParam(name = "banner-id", type = "string", required = true)
            @RequestParam(name = "banner-id") String bannerId) {

        log.info(REQUEST_MSG, BANNER_URL, bannerId);
        var response = ResponseModel.of(bannerService.deleteBanner(bannerId), OK);

        log.info(RESPONSE_MSG, BANNER_URL, bannerId);
        return ResponseEntity.ok(response);

    }
}
