package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.category.CategoryRqModel;
import com.budgetmanagementapp.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class CategoryBuilder {

    private final CategoryRepository categoryRepo;

    public Category buildCategory(CategoryRqModel requestBody, User user) {
        return categoryRepo.save(Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .icon(requestBody.getIcon())
                .name(requestBody.getCategoryName())
                .type(requestBody.getCategoryType().toUpperCase())
                .user(user)
                .build());
    }
}
