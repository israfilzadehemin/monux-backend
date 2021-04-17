package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_CREATE_CUSTOM_CATEGORY_URL;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_GET_ALL_CATEGORIES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_GET_CUSTOM_CATEGORIES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_UPDATE_CUSTOM_CATEGORY_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.CategoryRequestModel;
import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.UpdateCategoryModel;
import com.budgetmanagementapp.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(CATEGORY_CREATE_CUSTOM_CATEGORY_URL)
    public ResponseEntity<?> createCustomCategory(@RequestBody CategoryRequestModel requestBody,
                                                  Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_CREATE_CUSTOM_CATEGORY_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(categoryService.createCustomCategory(requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @GetMapping(CATEGORY_GET_ALL_CATEGORIES_URL)
    public ResponseEntity<?> getAllCategories(Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_GET_ALL_CATEGORIES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(categoryService.getCategoriesByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername(), true))
                        .build());
    }

    @GetMapping(CATEGORY_GET_CUSTOM_CATEGORIES_URL)
    public ResponseEntity<?> getCustomCategories(Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_GET_CUSTOM_CATEGORIES_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(categoryService.getCategoriesByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername(), false))
                        .build());
    }

    @PostMapping(CATEGORY_UPDATE_CUSTOM_CATEGORY_URL)
    public ResponseEntity<?> updateCustomCategory(@RequestBody UpdateCategoryModel requestBody,
                                                  Authentication auth) {

        log.info(format(REQUEST_MSG, CATEGORY_UPDATE_CUSTOM_CATEGORY_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(categoryService
                                .updateCategory(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

}