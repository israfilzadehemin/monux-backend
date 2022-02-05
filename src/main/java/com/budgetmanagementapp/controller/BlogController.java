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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Blog")
public class BlogController {

    private final BlogService blogService;

    @ApiOperation("Get all blogs")
    @GetMapping(BLOG_GET_ALL_BLOGS_URL)
    public ResponseEntity<ResponseModel<List<BlogRsModel>>> getAllBlogs(
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, BLOG_GET_ALL_BLOGS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(blogService.getAllBlogs(language), OK);

        log.info(RESPONSE_MSG, BLOG_GET_ALL_BLOGS_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get blog by id")
    @GetMapping(BLOG_GET_BLOG_BY_ID_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> getBlogById(
            @ApiParam(name = "blog-id", type = "string", required = true)
            @RequestParam(name = "blog-id") String blogId,
            @ApiParam(name = "language", type = "string", example = "en, az, ru", required = true)
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, BLOG_GET_BLOG_BY_ID_URL, blogId);
        var response = ResponseModel.of(blogService.getBlogById(blogId, language), OK);

        log.info(RESPONSE_MSG, BLOG_GET_BLOG_BY_ID_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create blog")
    @PostMapping(BLOG_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> createBlog(@RequestBody @Valid BlogRqModel request) {

        log.info(REQUEST_MSG, BLOG_URL, request);
        var response = ResponseModel.of(blogService.addBlog(request), CREATED);

        log.info(RESPONSE_MSG, BLOG_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update blog")
    @PutMapping(BLOG_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> updateBlog(@RequestBody @Valid UpdateBlogRqModel request) {

        log.info(REQUEST_MSG, BLOG_URL, request);
        var response = ResponseModel.of(blogService.updateBlog(request), OK);

        log.info(REQUEST_MSG, BLOG_URL, response);
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete blog")
    @DeleteMapping(BLOG_URL)
    public ResponseEntity<ResponseModel<BlogRsModel>> deleteBlog(
            @ApiParam(name = "blog-id", type = "string", required = true)
            @RequestParam(name = "blog-id") String blogId) {

        log.info(REQUEST_MSG, BLOG_URL, blogId);
        var response = ResponseModel.of(blogService.deleteBlog(blogId), OK);

        log.info(RESPONSE_MSG, BLOG_URL, response);
        return ResponseEntity.ok(response);
    }

}
