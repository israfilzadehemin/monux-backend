package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.RESPONSE_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.BLOGS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.PATH_ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@Tag(name = "Blog", description = "Blog operations")
@RequestMapping(BLOGS_URL)
public class BlogController {

    private final BlogService blogService;

    @Operation(description = "Get all blogs")
    @GetMapping
    public ResponseEntity<ResponseModel<List<BlogRsModel>>> getAllBlogs(
            @Parameter(example = "en, az, ru")
            @RequestParam(name = "language") String language) {

        log.info(REQUEST_MSG, BLOGS_URL, NO_BODY_MSG);
        var response = ResponseModel.of(blogService.getAllBlogs(language), OK);

        log.info(RESPONSE_MSG, BLOGS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Get blog by id")
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

    @Operation(description = "Create blog")
    @PostMapping
    public ResponseEntity<ResponseModel<BlogRsModel>> createBlog(@RequestBody @Valid BlogRqModel request) {

        log.info(REQUEST_MSG, BLOGS_URL, request);
        var response = ResponseModel.of(blogService.addBlog(request), CREATED);

        log.info(RESPONSE_MSG, BLOGS_URL, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Update blog")
    @PutMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BlogRsModel>> updateBlog(
            @RequestBody @Valid BlogRqModel request,
            @PathVariable("id") String blogId) {

        log.info(REQUEST_MSG, BLOGS_URL + PATH_ID, request);
        var response = ResponseModel.of(blogService.updateBlog(request, blogId), OK);

        log.info(REQUEST_MSG, BLOGS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Delete blog")
    @DeleteMapping(PATH_ID)
    public ResponseEntity<ResponseModel<BlogRsModel>> deleteBlog(
            @PathVariable(name = "id") String blogId) {

        log.info(REQUEST_MSG, BLOGS_URL + PATH_ID, blogId);
        var response = ResponseModel.of(blogService.deleteBlog(blogId), OK);

        log.info(RESPONSE_MSG, BLOGS_URL + PATH_ID, response);
        return ResponseEntity.ok(response);
    }

}
