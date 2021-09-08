package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.category.CategoryRqModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CategoryBuilder {
    public Category buildCategory(CategoryRqModel requestBody, User user) {
        return Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .icon(requestBody.getIcon())
                .name(requestBody.getCategoryName())
                .type(requestBody.getCategoryType().toUpperCase())
                .user(user)
                .build();
    }
}
