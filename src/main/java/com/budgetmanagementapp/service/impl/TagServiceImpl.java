package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.GENERAL_USERNAME;
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
import com.budgetmanagementapp.model.TagRequestModel;
import com.budgetmanagementapp.model.TagResponseModel;
import com.budgetmanagementapp.model.UpdateTagModel;
import com.budgetmanagementapp.repository.TagRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.TagService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class TagServiceImpl implements TagService {
    private final UserRepository userRepo;
    private final TagRepository tagRepo;

    @Override
    public TagResponseModel createTag(TagRequestModel requestBody, String username) {
        CustomValidator.validateTagRequestModel(requestBody);

        User user = userByUsername(username);
        checkDuplicate(requestBody.getTagName(), user);
        Tag tag = buildTag(requestBody, user);

        log.info(String.format(TAG_CREATED_MSG, user.getUsername(), buildTagResponseModel(tag)));
        return buildTagResponseModel(tag);

    }

    private void checkDuplicate(String tagName, User user) {
        if (tagRepo.byNameAndUser(tagName, user).isPresent()
                || tagRepo.byNameAndUser(tagName, userByUsername(GENERAL_USERNAME)).isPresent()) {
            throw new DuplicateTagException(format(DUPLICATE_TAG_NAME_MSG, user.getUsername(), tagName));
        }
    }

    @Override
    public List<TagResponseModel> getTagsByUser(String username, boolean includeCommonTags) {
        User user = userByUsername(username);

        List<TagResponseModel> tags = new ArrayList<>();

        if (includeCommonTags) {
            tagRepo.findAll().stream()
                    .map(this::buildTagResponseModel)
                    .forEach(tags::add);
        }

        tagRepository.allByUser(user).stream()
                .map(this::buildTagResponseModel)
                .forEach(tags::add);

        if (tags.isEmpty()) {
            throw new TagNotFoundException(String.format(TAG_NOT_FOUND_MSG, username));
        }

        tags.sort(Comparator.comparing(TagResponseModel::getTagName));

        log.info(String.format(ALL_TAGS_MSG, user.getUsername(), tags));
        return tags;

    }

    @Override
    public TagResponseModel updateTag(UpdateTagModel requestBody, String username) {
        CustomValidator.validateUpdateTagModel(requestBody);

        Tag tag =
                tagRepository.byIdAndUser(requestBody.getTagId(), userByUsername(username))
                        .orElseThrow(
                                () -> new TagNotFoundException(String.format(UNAUTHORIZED_TAG_MSG, username,
                                        requestBody.getTagId())));

        tag.setName(requestBody.getNewTagName());
        tagRepository.save(tag);

        log.info(String.format(TAG_UPDATED_MSG, username, buildTagResponseModel(tag)));
        return buildTagResponseModel(tag);
    }

    @Override
    public TagResponseModel toggleVisibility(String tagId, String username) {
        CustomValidator.validateTagId(tagId);

        Tag tag = tagRepository
                .byIdAndUser(tagId, userByUsername(username))
                .orElseThrow(() -> new TagNotFoundException(
                        String.format(UNAUTHORIZED_TAG_MSG, username, tagId)));

        tag.setVisibility(!tag.isVisibility());
        tagRepository.save(tag);

        log.info(String.format(VISIBILITY_TOGGLED_MSG, username, buildTagResponseModel(tag)));
        return buildTagResponseModel(tag);
    }

    private User userByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    private Tag buildTag(TagRequestModel requestBody, User user) {
        CustomValidator.validateCategoryType(requestBody.getTagCategory());

        return tagRepo.save(Tag.builder()
                .tagId(UUID.randomUUID().toString())
                .name(requestBody.getTagName())
                .type(requestBody.getTagCategory())
                .visibility(true)
                .user(user)
                .build());
    }

    private TagResponseModel buildTagResponseModel(Tag tag) {
        return TagResponseModel.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getName())
                .tagCategory(tag.getType())
                .visibility(tag.isVisibility())
                .build();
    }

}
