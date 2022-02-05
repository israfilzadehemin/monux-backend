package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.category.CategoryRqModel;
import com.budgetmanagementapp.model.category.CategoryRsModel;
import com.budgetmanagementapp.model.category.UpdateCategoryRqModel;
import com.budgetmanagementapp.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Log4j2
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("Create category")
    @PostMapping(CATEGORY_URL)
    public ResponseEntity<ResponseModel<CategoryRsModel>> createCategory(
            @RequestBody @Valid CategoryRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, CATEGORY_URL, requestBody);
        var response =
                ResponseModel.of(
                        categoryService.createCategory(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        CREATED);

        log.info(RESPONSE_MSG, CATEGORY_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all categories")
    @GetMapping(CATEGORY_GET_ALL_CATEGORIES_URL)
    public ResponseEntity<ResponseModel<List<CategoryRsModel>>> getAllCategories(Authentication auth) {

        log.info(REQUEST_MSG, CATEGORY_GET_ALL_CATEGORIES_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        categoryService.getCategoriesByUser(((UserDetails) auth.getPrincipal()).getUsername(), true),
                        OK);

        log.info(REQUEST_MSG, CATEGORY_GET_ALL_CATEGORIES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get all categories of user")
    @GetMapping(CATEGORY_GET_CATEGORIES_URL)
    public ResponseEntity<ResponseModel<List<CategoryRsModel>>> getCategoriesOfUser(Authentication auth) {

        log.info(REQUEST_MSG, CATEGORY_GET_CATEGORIES_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        categoryService.getCategoriesByUser(((UserDetails) auth.getPrincipal()).getUsername(), false),
                        OK);

        log.info(RESPONSE_MSG, CATEGORY_GET_CATEGORIES_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update category")
    @PutMapping(CATEGORY_URL)
    public ResponseEntity<ResponseModel<CategoryRsModel>> updateCustomCategory(
            @RequestBody @Valid UpdateCategoryRqModel requestBody, Authentication auth) {

        log.info(REQUEST_MSG, CATEGORY_URL, requestBody);
        var response =
                ResponseModel.of(
                        categoryService.updateCategory(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(RESPONSE_MSG, CATEGORY_URL, response);
        return ResponseEntity.ok(response);
    }

}
