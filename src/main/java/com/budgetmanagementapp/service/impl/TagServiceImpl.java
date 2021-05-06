package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.COMMON_USERNAME;
import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_TAGS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DUPLICATE_TAG_NAME_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TAG_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TAG_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TAG_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_TAG_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.VISIBILITY_TOGGLED_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.DuplicateTagException;
import com.budgetmanagementapp.exception.TagNotFoundException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.TagRqModel;
import com.budgetmanagementapp.model.TagRsModel;
import com.budgetmanagementapp.model.UpdateTagRqModel;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.TagService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class TagServiceImpl implements TagService {
    private final UserService userService;
    private final TagRepository tagRepo;

    @Override
    public TagRsModel createTag(TagRqModel requestBody, String username) {
        User user = userByUsername(username);
        checkDuplicate(requestBody.getTagName(), user);
        Tag tag = buildTag(requestBody, user);

        log.info(format(TAG_CREATED_MSG, user.getUsername(), buildTagResponseModel(tag)));
        return buildTagResponseModel(tag);
    }

    @Override
    public List<TagRsModel> getTagsByUser(String username, boolean includeCommonTags) {
        User user = userByUsername(username);
        User generalUser = userByUsername(COMMON_USERNAME);

        List<TagRsModel> tags = tagsByUser(includeCommonTags, user, generalUser);

        if (tags.isEmpty()) {
            throw new TagNotFoundException(format(TAG_NOT_FOUND_MSG, username));
        }

        log.info(format(ALL_TAGS_MSG, user.getUsername(), tags));
        return tags;
    }

    @Override
    public TagRsModel updateTag(UpdateTagRqModel requestBody, String username) {
        Tag tag = byIdAndUser(requestBody.getTagId(), username);
        updateTagValues(requestBody, tag);

        log.info(format(TAG_UPDATED_MSG, username, buildTagResponseModel(tag)));
        return buildTagResponseModel(tag);
    }

    @Override
    public TagRsModel toggleVisibility(String tagId, String username) {
        Tag tag = byIdAndUser(tagId, username);
        toggleTagVisibility(tag);

        log.info(format(VISIBILITY_TOGGLED_MSG, username, buildTagResponseModel(tag)));
        return buildTagResponseModel(tag);
    }

    @Override
    public List<Tag> allByIdsAndTypeAndUser(List<String> tagIds, String type, User user) {
        return tagIds
                .stream()
                .filter(id -> byIdAndTypeAndUser(id, type, user).isPresent())
                .map(id -> byIdAndTypeAndUser(id, type, user).get())
                .collect(Collectors.toList());
    }

    private Tag buildTag(TagRqModel requestBody, User user) {
        CustomValidator.validateCategoryType(requestBody.getTagCategory());

        return tagRepo.save(Tag.builder()
                .tagId(UUID.randomUUID().toString())
                .name(requestBody.getTagName())
                .type(requestBody.getTagCategory())
                .visibility(true)
                .user(user)
                .build());
    }

    private TagRsModel buildTagResponseModel(Tag tag) {
        return TagRsModel.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getName())
                .tagCategory(tag.getType())
                .visibility(tag.isVisibility())
                .build();
    }

    private User userByUsername(String username) {
        return userService.findByUsername(username);
    }

    private List<TagRsModel> tagsByUser(boolean includeCommonTags, User user, User generalUser) {
        return includeCommonTags
                ? tagRepo.allByUserOrGeneralUser(user, generalUser)
                .stream()
                .map(this::buildTagResponseModel)
                .collect(Collectors.toList())
                : tagRepo.allByUser(user)
                .stream()
                .map(this::buildTagResponseModel)
                .collect(Collectors.toList());
    }

    private Tag byIdAndUser(String tagId, String username) {
        return tagRepo.byIdAndUser(tagId, userByUsername(username))
                .orElseThrow(() -> new TagNotFoundException(format(UNAUTHORIZED_TAG_MSG, username, tagId)));
    }

    private Optional<Tag> byIdAndTypeAndUser(String tagId, String type, User user) {
        return tagRepo.byIdAndTypeAndUsers(
                tagId,
                type,
                Arrays.asList(user, userService.findByUsername(COMMON_USERNAME)));
    }

    private void updateTagValues(UpdateTagRqModel requestBody, Tag tag) {
        CustomValidator.validateCategoryType(requestBody.getTagCategory());
        tag.setName(requestBody.getTagName());
        tag.setType(requestBody.getTagCategory());
        tagRepo.save(tag);
    }

    private void toggleTagVisibility(Tag tag) {
        tag.setVisibility(!tag.isVisibility());
        tagRepo.save(tag);
    }

    private void checkDuplicate(String tagName, User user) {
        if (tagRepo.byNameAndUser(tagName, user).isPresent()
                || tagRepo.byNameAndUser(tagName, userByUsername(COMMON_USERNAME)).isPresent()) {
            throw new DuplicateTagException(format(DUPLICATE_TAG_NAME_MSG, user.getUsername(), tagName));
        }
    }

}
