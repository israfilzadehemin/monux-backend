package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.BLOGS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Blog")
@RequestMapping(BLOGS_URL)
public class BlogController {

    private final BlogService blogService;

    @ApiOperation("Get all blogs")
    @GetMapping
    public ResponseEntity<ResponseModel<List<BlogRsModel>>> getAllBlogs(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, BLOGS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(blogService.getAllBlogs(language), OK);

        log.info(RESPONSE_MSG, BLOGS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get blog by id")
    @GetMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BlogRsModel>> getBlogById(
            @PathVariable(name = "id") String blogId,
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, BLOGS_URL + PATH_ID, blogId);
        var response = ResponseModel.of(blogService.getBlogById(blogId, language), OK);

        log.info(RESPONSE_MSG, BLOGS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create blog")
    @PostMapping
    public ResponseEntity<ResponseModel<BlogRsModel>> createBlog(@RequestBody @Valid BlogRqModel request) {

        log.info(REQUEST_MSG, BLOGS_URL, request);
        var response = ResponseModel.of(blogService.addBlog(request), CREATED);

        log.info(RESPONSE_MSG, BLOGS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update blog")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BlogRsModel>> updateBlog(
            @RequestBody @Valid BlogRqModel request,
            @PathVariable("id") String blogId) {

        log.info(REQUEST_MSG, BLOGS_URL + PATH_ID, request);
        var response = ResponseModel.of(blogService.updateBlog(request, blogId), OK);

        log.info(REQUEST_MSG, BLOGS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete blog")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BlogRsModel>> deleteBlog(
            @PathVariable(name = "id") String blogId) {

        log.info(REQUEST_MSG, BLOGS_URL + PATH_ID, blogId);
        var response = ResponseModel.of(blogService.deleteBlog(blogId), OK);

        log.info(RESPONSE_MSG, BLOGS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

}
