package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.LabelRqModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LabelBuilder {
    public Label buildLabel(LabelRqModel requestBody, User user) {
        return Label.builder()
                .labelId(UUID.randomUUID().toString())
                .name(requestBody.getLabelName())
                .type(requestBody.getLabelCategory())
                .visibility(true)
                .user(user)
                .build();
    }
}
