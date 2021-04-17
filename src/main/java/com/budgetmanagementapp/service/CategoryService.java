package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.CategoryRequestModel;
import com.budgetmanagementapp.model.CategoryResponseModel;
import com.budgetmanagementapp.model.UpdateCategoryModel;
import java.util.List;

public interface CategoryService {

    CategoryResponseModel createCustomCategory(CategoryRequestModel requestBody, String username);

    List<CategoryResponseModel> getCategoriesByUser(String username, boolean includeDefaultCategories);

    CategoryResponseModel updateCategory(UpdateCategoryModel requestBody, String username);
}
