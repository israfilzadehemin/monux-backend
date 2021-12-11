package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.model.blog.UpdateBlogRqModel;
import com.budgetmanagementapp.service.BlogService;
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

import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Blog")
public class BlogController {

    private final BlogService blogService;

    @ApiOperation("Get all blogs")
    @GetMapping(BLOG_GET_ALL_BLOGS_URL)
    public ResponseEntity<ResponseModel<List<BlogRsModel>>> getAllBlogs(@RequestParam String language) {

        log.info(format(REQUEST_MSG, BLOG_GET_ALL_BLOGS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.of(blogService.getAllBlogs(language), HttpStatus.OK));
    }

    @ApiOperation("Get blog by id")
    @GetMapping(BLOG_GET_BLOG_BY_ID_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> getBlogById(
            @ApiParam(
                    name = "blog-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "blog-id") String blogId) {

        log.info(format(BLOG_WITH_PARAM, BLOG_GET_BLOG_BY_ID_URL, blogId));
        return ResponseEntity.ok(
                ResponseModel.of(blogService.getBlogById(blogId), HttpStatus.OK));
    }

    @ApiOperation("Create blog")
    @PostMapping(BLOG_CREATE_BLOG_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> createBlog(@RequestBody @Valid BlogRqModel request) {

        log.info(format(REQUEST_MSG, BLOG_CREATE_BLOG_URL, request));
        return ResponseEntity.ok(
                ResponseModel.of(blogService.addBlog(request), HttpStatus.CREATED));
    }

    @ApiOperation("Update blog")
    @PostMapping(BLOG_UPDATE_BLOG_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> updateBlog(@RequestBody @Valid UpdateBlogRqModel request) {

        log.info(format(REQUEST_MSG, BLOG_UPDATE_BLOG_URL, request));
        return ResponseEntity.ok(
                ResponseModel.of(blogService.updateBlog(request), HttpStatus.OK));
    }

    @ApiOperation("Delete blog")
    @PostMapping(BLOG_DELETE_BLOG_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> deleteBlog(
            @ApiParam(
                    name = "blog-id",
                    type = "string",
                    example = "",
                    required = true)
            @RequestParam(name = "blog-id") String blogId) {

        log.info(format(BLOG_WITH_PARAM, BLOG_DELETE_BLOG_URL, blogId));
        return ResponseEntity.ok(
                ResponseModel.of(blogService.deleteBlog(blogId), HttpStatus.OK));
    }

}
