package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.banner.BannerRqModel;
import com.budgetmanagementapp.model.banner.BannerRsModel;
import com.budgetmanagementapp.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.BANNERS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Banner")
@RequestMapping(BANNERS_URL)
public class BannerController {

    private final BannerService bannerService;

    @ApiOperation("Get banner by id")
    @GetMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerById(
            @PathVariable("id") String bannerId,
            @Parameter(example = "az, en, ru")
            @RequestParam("language") String language) {

        log.info(REQUEST_MSG, BANNERS_URL + PATH_ID, bannerId);
        var response = ResponseModel.of(bannerService.getBannerById(bannerId, language), OK);

        log.info(RESPONSE_MSG, BANNERS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get banner by keyword")
    @GetMapping
    public ResponseEntity<ResponseModel<BannerRsModel>> getBannerByKeyword(
            @RequestParam("keyword") String keyword,
            @Parameter(example = "az, en, ru")
            @RequestParam("language") String language) {

        log.info(REQUEST_MSG, BANNERS_URL, keyword);
        var response = ResponseModel.of(bannerService.getBannerByKeyword(keyword, language), OK);

        log.info(RESPONSE_MSG, BANNERS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create banner")
    @PostMapping
    public ResponseEntity<ResponseModel<BannerRsModel>> createBanner(@RequestBody @Valid BannerRqModel request) {

        log.info(REQUEST_MSG, BANNERS_URL, request);
        var response = ResponseModel.of(bannerService.createBanner(request), CREATED);

        log.info(RESPONSE_MSG, BANNERS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update banner")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BannerRsModel>> updateBanner(
            @RequestBody @Valid BannerRqModel request,
            @PathVariable("id") String bannerId) {

        var response = ResponseModel.of(bannerService.updateBanner(request, bannerId), OK);

        log.info(RESPONSE_MSG, BANNERS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete banner")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BannerRsModel>> deleteBanner(
            @PathVariable(name = "id") String bannerId) {

        log.info(REQUEST_MSG, BANNERS_URL + PATH_ID, bannerId);
        var response = ResponseModel.of(bannerService.deleteBanner(bannerId), OK);

        log.info(RESPONSE_MSG, BANNERS_URL + PATH_ID, bannerId);
        return ResponseEntity.ok(response);

    }
}
