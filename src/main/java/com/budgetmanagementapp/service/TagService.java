package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.TagRqModel;
import com.budgetmanagementapp.model.TagRsModel;
import com.budgetmanagementapp.model.UpdateTagRqModel;
import java.util.List;

public interface TagService {
    TagRsModel createTag(TagRqModel requestBody, String username);

    List<TagRsModel> getTagsByUser(String username, boolean includeCommonTags);

    TagRsModel updateTag(UpdateTagRqModel requestBody, String username);

    TagRsModel toggleVisibility(String tagId, String username);
}
