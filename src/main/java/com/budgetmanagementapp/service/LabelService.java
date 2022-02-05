package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.label.LabelRqModel;
import com.budgetmanagementapp.model.label.LabelRsModel;
import com.budgetmanagementapp.model.label.UpdateLabelRqModel;

import java.util.List;

public interface LabelService {
    LabelRsModel createLabel(LabelRqModel requestBody, String username);

    List<LabelRsModel> getLabelsByUser(String username, boolean includeCommonLabels);

    LabelRsModel updateLabel(UpdateLabelRqModel requestBody, String username);

    LabelRsModel toggleVisibility(String labelId, String username);

    List<Label> allByIdsAndTypeAndUser(List<String> labelIds, String type, User user);
}
