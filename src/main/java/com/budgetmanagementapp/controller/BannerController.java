package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.BANNER_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.BANNER_DELETE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.BANNER_GET_BANNER_BY_ID_URL;
import static com.budgetmanagementapp.utility.UrlConstant.BANNER_GET_BANNER_BY_KEYWORD_URL;
import static com.budgetmanagementapp.utility.UrlConstant.BANNER_UPDATE_URL;
import static org.springframework.http.HttpStatus.*;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.model.home.BannerRsModel;
import com.budgetmanagementapp.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Banner")
public class BannerController {

    private final BannerService bannerService;

    @ApiOperation("Get banner by id")
    @GetMapping(BANNER_GET_BANNER_BY_ID_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerById(
            @ApiParam(name = "banner-id", type = "string", example = "", required = true)
            @RequestParam("banner-id") String bannerId,
            @ApiParam(name = "language", type = "string", example = "az, en, ru", required = true)
            @RequestParam("language") String language) {

        log.info(REQUEST_MSG, BANNER_GET_BANNER_BY_ID_URL, bannerId);
        var response = ResponseModel.of(bannerService.getBannerById(bannerId, language), OK);

        log.info(RESPONSE_MSG, BANNER_GET_BANNER_BY_ID_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get banner by keyword")
    @GetMapping(BANNER_GET_BANNER_BY_KEYWORD_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerByKeyword(
            @ApiParam(name = "banner-keyword", type = "string", example = "", required = true)
            @RequestParam("banner-keyword") String keyword,
            @ApiParam(name = "language", type = "string", example = "az, en, ru", required = true)
            @RequestParam("language") String language) {

        log.info(REQUEST_MSG, BANNER_GET_BANNER_BY_KEYWORD_URL, keyword);
        var response = ResponseModel.of(bannerService.getBannerByKeyword(keyword, language), OK);

        log.info(RESPONSE_MSG, BANNER_GET_BANNER_BY_KEYWORD_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create banner")
    @PostMapping(BANNER_CREATE_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> createBanner(@RequestBody @Valid BannerRqModel request) {

        log.info(REQUEST_MSG, BANNER_CREATE_URL, request);
        var response = ResponseModel.of(bannerService.createBanner(request), CREATED);

        log.info(RESPONSE_MSG, BANNER_CREATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update banner")
    @PostMapping(BANNER_UPDATE_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> updateBanner(
            @RequestBody @Valid BannerRqModel request,
            @ApiParam(name = "banner-id", type = "string", example = "", required = true)
            @RequestParam(name = "banner-id") String bannerId) {

        log.info(REQUEST_MSG, BANNER_UPDATE_URL, request);
        var response = ResponseModel.of(bannerService.updateBanner(request, bannerId), OK);

        log.info(RESPONSE_MSG, BANNER_UPDATE_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete banner")
    @PostMapping(BANNER_DELETE_URL)
    public ResponseEntity<ResponseModel<BannerRsModel>> deleteBanner(
            @ApiParam(name = "banner-id", type = "string", example = "", required = true)
            @RequestParam(name = "banner-id") String bannerId) {

        log.info(REQUEST_MSG, BANNER_DELETE_URL, bannerId);
        var response = ResponseModel.of(bannerService.deleteBanner(bannerId), OK);

        log.info(RESPONSE_MSG, BANNER_DELETE_URL, bannerId);
        return ResponseEntity.ok(response);

    }
}
