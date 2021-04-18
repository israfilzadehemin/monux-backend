package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_TAGS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CUSTOM_TAG_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CUSTOM_TAG_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DUPLICATE_TAG_NAME_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TAG_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_TAG_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.VISIBILITY_TOGGLED_MSG;

import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.DuplicateTagException;
import com.budgetmanagementapp.exception.TagNotFoundException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.TagRequestModel;
import com.budgetmanagementapp.model.TagResponseModel;
import com.budgetmanagementapp.model.UpdateTagModel;
import com.budgetmanagementapp.repository.CustomTagRepository;
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
    private final CustomTagRepository customTagRepo;
    private final TagRepository tagRepo;

    @Override
    public TagResponseModel createCustomTag(TagRequestModel requestBody, String username) {
        CustomValidator.validateTagRequestModel(requestBody);

        User user = findUserByUsername(username);

        if (customTagRepo.byNameAndUser(requestBody.getTagName(), user).isPresent()) {
            throw new DuplicateTagException(String.format(DUPLICATE_TAG_NAME_MSG, username, requestBody.getTagName()));
        }

        CustomTag customTag = buildCustomTag(requestBody, user);

        log.info(String.format(CUSTOM_TAG_CREATED_MSG, user.getUsername(), buildCustomTagResponseModel(customTag)));
        return buildCustomTagResponseModel(customTag);

    }

    @Override
    public List<TagResponseModel> getTagsByUser(String username, boolean includeDefaultTags) {
        User user = findUserByUsername(username);

        List<TagResponseModel> tags = new ArrayList<>();

        if (includeDefaultTags) {
            tagRepo.findAll().stream()
                    .map(this::buildTagResponseModel)
                    .forEach(tags::add);
        }

        customTagRepo.allByUser(user).stream()
                .map(this::buildCustomTagResponseModel)
                .forEach(tags::add);

        if (tags.isEmpty()) {
            throw new TagNotFoundException(String.format(CUSTOM_TAG_NOT_FOUND_MSG, username));
        }

        tags.sort(Comparator.comparing(TagResponseModel::getTagName));

        log.info(String.format(ALL_TAGS_MSG, user.getUsername(), tags));
        return tags;

    }

    @Override
    public TagResponseModel updateTag(UpdateTagModel requestBody, String username) {
        CustomValidator.validateUpdateTagModel(requestBody);

        CustomTag tag =
                customTagRepo.byIdAndUser(requestBody.getTagId(), findUserByUsername(username))
                        .orElseThrow(
                                () -> new TagNotFoundException(String.format(UNAUTHORIZED_TAG_MSG, username,
                                        requestBody.getTagId())));

        tag.setName(requestBody.getNewTagName());
        customTagRepo.save(tag);

        log.info(String.format(TAG_UPDATED_MSG, username, buildCustomTagResponseModel(tag)));
        return buildCustomTagResponseModel(tag);
    }

    @Override
    public TagResponseModel toggleVisibility(String tagId, String username) {
        CustomValidator.validateTagId(tagId);

        CustomTag tag = customTagRepo
                .byIdAndUser(tagId, findUserByUsername(username))
                .orElseThrow(() -> new TagNotFoundException(
                        String.format(UNAUTHORIZED_TAG_MSG, username, tagId)));

        tag.setVisibility(!tag.isVisibility());
        customTagRepo.save(tag);

        log.info(String.format(VISIBILITY_TOGGLED_MSG, username, buildCustomTagResponseModel(tag)));
        return buildCustomTagResponseModel(tag);
    }

    private User findUserByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    private CustomTag buildCustomTag(TagRequestModel requestBody, User user) {
        return customTagRepo.save(CustomTag.builder()
                .customTagId(UUID.randomUUID().toString())
                .name(requestBody.getTagName())
                .visibility(true)
                .user(user)
                .build());
    }

    private TagResponseModel buildCustomTagResponseModel(CustomTag customTag) {
        return TagResponseModel.builder()
                .tagId(customTag.getCustomTagId())
                .tagName(customTag.getName())
                .visibility(customTag.isVisibility())
                .build();
    }

    private TagResponseModel buildTagResponseModel(Tag tag) {
        return TagResponseModel.builder()
                .tagId(tag.getTagId())
                .tagName(tag.getName())
                .visibility(tag.isVisibility())
                .build();
    }

}
