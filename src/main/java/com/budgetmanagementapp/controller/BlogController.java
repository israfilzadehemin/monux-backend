package com.budgetmanagementapp.controller;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.UpdateBlogRqModel;
import com.budgetmanagementapp.service.BlogService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.UrlConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@RestController
public class BlogController {

    private final BlogService blogService;

    @GetMapping(BLOG_GET_ALL_BLOGS_URL)
    public ResponseEntity<?> getAllBlogs() {

        log.info(format(REQUEST_MSG, BLOG_GET_ALL_BLOGS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(blogService.getAllBlogs())
                        .build());
    }

    @GetMapping(BLOG_GET_BLOG_BY_ID_URL)
    public ResponseEntity<?> getBlogById(@RequestParam(name = "blog-id") String blogId) {

        log.info(format(BLOG_WITH_PARAM, BLOG_GET_BLOG_BY_ID_URL, blogId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(blogService.getBlogById(blogId))
                        .build());
    }

    @PostMapping(BLOG_CREATE_BLOG_URL)
    public ResponseEntity<?> createBlog(@RequestBody @Valid BlogRqModel request) {

        log.info(format(REQUEST_MSG, BLOG_CREATE_BLOG_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(blogService.addBlog(request))
                        .build());
    }

    @PostMapping(BLOG_UPDATE_BLOG_URL)
    public ResponseEntity<?> updateBlog(@RequestBody @Valid UpdateBlogRqModel request) {

        log.info(format(REQUEST_MSG, BLOG_UPDATE_BLOG_URL, request));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(blogService.updateBlog(request))
                        .build());
    }

    @PostMapping(BLOG_DELETE_BLOG_URL)
    public ResponseEntity<?> deleteBlog(@RequestParam(name = "blog-id") String blogId) {

        log.info(format(BLOG_WITH_PARAM, BLOG_DELETE_BLOG_URL, blogId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(blogService.deleteBlog(blogId))
                        .build());
    }

}
