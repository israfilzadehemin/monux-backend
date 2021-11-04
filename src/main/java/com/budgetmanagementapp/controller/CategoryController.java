package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_GET_ALL_CATEGORIES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_GET_CATEGORIES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_UPDATE_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.category.CategoryRqModel;
import com.budgetmanagementapp.model.category.CategoryRsModel;
import com.budgetmanagementapp.model.category.UpdateCategoryRqModel;
import com.budgetmanagementapp.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("Create category")
    @PostMapping(CATEGORY_CREATE_URL)
    public ResponseEntity<ResponseModel<CategoryRsModel>> createCategory(@RequestBody @Valid CategoryRqModel requestBody,
                                            Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_CREATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(categoryService
                        .createCategory(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        HttpStatus.CREATED));
    }

    @ApiOperation("Get all categories")
    @GetMapping(CATEGORY_GET_ALL_CATEGORIES_URL)
    public ResponseEntity<ResponseModel<List<CategoryRsModel>>> getAllCategories(Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_GET_ALL_CATEGORIES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(categoryService.getCategoriesByUser(
                        ((UserDetails) auth.getPrincipal()).getUsername(), true), HttpStatus.OK));
    }

    @ApiOperation("Get all categories of user")
    @GetMapping(CATEGORY_GET_CATEGORIES_URL)
    public ResponseEntity<ResponseModel<List<CategoryRsModel>>> getCategoriesOfUser(Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_GET_CATEGORIES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(categoryService.getCategoriesByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername(), false), HttpStatus.OK));
    }

    @ApiOperation("Update category")
    @PostMapping(CATEGORY_UPDATE_URL)
    public ResponseEntity<ResponseModel<CategoryRsModel>> updateCustomCategory(@RequestBody @Valid UpdateCategoryRqModel requestBody,
                                                  Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_UPDATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.of(categoryService
                                .updateCategory(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                                HttpStatus.OK));
    }

}
