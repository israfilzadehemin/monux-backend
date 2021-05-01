package com.budgetmanagementapp.controller;

import static com.budgetmanagementapp.utility.MsgConstant.NO_BODY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.REQUEST_MSG;
import static com.budgetmanagementapp.utility.UrlConstant.TAG_CREATE_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TAG_GET_ALL_TAGS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TAG_GET_TAGS_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TAG_TOGGLE_VISIBILITY_URL;
import static com.budgetmanagementapp.utility.UrlConstant.TAG_UPDATE_URL;
import static java.lang.String.format;

import com.budgetmanagementapp.model.ResponseModel;
import com.budgetmanagementapp.model.TagRqModel;
import com.budgetmanagementapp.model.UpdateTagRqModel;
import com.budgetmanagementapp.service.TagService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class TagController {

    private final TagService tagService;

    private static final String REQUEST_PARAM_TAG_ID = "tag-id";

    @PostMapping(TAG_CREATE_URL)
    public ResponseEntity<?> createTag(@RequestBody @Valid TagRqModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, TAG_CREATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.CREATED)
                        .body(tagService.createTag(requestBody,
                                ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @GetMapping(TAG_GET_ALL_TAGS_URL)
    public ResponseEntity<?> getAllTags(Authentication auth) {

        log.info(format(REQUEST_MSG, TAG_GET_ALL_TAGS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(tagService.getTagsByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername(), true))
                        .build());
    }

    @GetMapping(TAG_GET_TAGS_URL)
    public ResponseEntity<?> getTagsOfUser(Authentication auth) {

        log.info(format(REQUEST_MSG, TAG_GET_TAGS_URL, NO_BODY_MSG));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(tagService.getTagsByUser(
                                ((UserDetails) auth.getPrincipal()).getUsername(), false))
                        .build());
    }

    @PostMapping(TAG_UPDATE_URL)
    public ResponseEntity<?> updateTag(@RequestBody @Valid UpdateTagRqModel requestBody, Authentication auth) {

        log.info(format(REQUEST_MSG, TAG_UPDATE_URL, requestBody));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(tagService
                                .updateTag(requestBody, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());
    }

    @PostMapping(TAG_TOGGLE_VISIBILITY_URL)
    public ResponseEntity<?> toggleVisibility(
            @RequestParam(name = REQUEST_PARAM_TAG_ID) String tagId, Authentication auth) {
        log.info(format(REQUEST_MSG, TAG_TOGGLE_VISIBILITY_URL, tagId));
        return ResponseEntity.ok(
                ResponseModel.builder()
                        .status(HttpStatus.OK)
                        .body(tagService
                                .toggleVisibility(tagId, ((UserDetails) auth.getPrincipal()).getUsername()))
                        .build());

    }
}
