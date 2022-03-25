package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORIES_URL;
import static com.budgetmanagementapp.utility.UrlConstant.CATEGORY_BY_USER_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.category.CategoryRqModel;
import com.budgetmanagementapp.model.category.CategoryRsModel;
import com.budgetmanagementapp.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Category", description = "Category operations")
@RequestMapping(CATEGORIES_URL)
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(description = "Create category")
    @PostMapping
    public ResponseEntity<ResponseModel<CategoryRsModel>> createCategory(
            @RequestBody @Valid CategoryRqModel requestBody, @Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, CATEGORIES_URL, requestBody);
        var response =
                ResponseModel.of(
                        categoryService.createCategory(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()),
                        CREATED);

        log.info(RESPONSE_MSG, CATEGORIES_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get all categories")
    @GetMapping
    public ResponseEntity<ResponseModel<List<CategoryRsModel>>> getAllCategories(@Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, CATEGORIES_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        categoryService.getCategoriesByUser(((UserDetails) auth.getPrincipal()).getUsername(), true),
                        OK);

        log.info(REQUEST_MSG, CATEGORIES_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get all categories of user")
    @GetMapping(CATEGORY_BY_USER_URL)
    public ResponseEntity<ResponseModel<List<CategoryRsModel>>> getCategoriesOfUser(@Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, CATEGORIES_URL + CATEGORY_BY_USER_URL, NO_BODY_MSG);
        var response =
                ResponseModel.of(
                        categoryService.getCategoriesByUser(((UserDetails) auth.getPrincipal()).getUsername(), false),
                        OK);

        log.info(RESPONSE_MSG, CATEGORIES_URL + CATEGORY_BY_USER_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update category")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<CategoryRsModel>> updateCustomCategory(
            @RequestBody @Valid CategoryRqModel requestBody,
            @PathVariable("id") String categoryId, @Parameter(hidden = true) Authentication auth) {

        log.info(REQUEST_MSG, CATEGORIES_URL + PATH_ID, requestBody);
        var response =
                ResponseModel.of(
                        categoryService.updateCategory(requestBody, categoryId, ((UserDetails) auth.getPrincipal()).getUsername()),
                        OK);

        log.info(RESPONSE_MSG, CATEGORIES_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

}
