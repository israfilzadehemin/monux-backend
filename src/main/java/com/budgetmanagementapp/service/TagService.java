package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.TagRequestModel;
import com.budgetmanagementapp.model.TagResponseModel;
import com.budgetmanagementapp.model.UpdateTagRequestModel;
import java.util.List;

public interface TagService {
    TagResponseModel createTag(TagRequestModel requestBody, String username);

    List<TagResponseModel> getTagsByUser(String username, boolean includeCommonTags);

    TagResponseModel updateTag(UpdateTagRequestModel requestBody, String username);

    TagResponseModel toggleVisibility(String tagId, String username);
}
