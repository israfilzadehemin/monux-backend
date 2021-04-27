package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.CategoryRequestModel;
import com.budgetmanagementapp.model.CategoryResponseModel;
import com.budgetmanagementapp.model.UpdateCategoryRequestModel;
import java.util.List;

public interface CategoryService {

    CategoryResponseModel createCategory(CategoryRequestModel requestBody, String username);

    List<CategoryResponseModel> getCategoriesByUser(String username, boolean includeCommonCategories);

    CategoryResponseModel updateCategory(UpdateCategoryRequestModel requestBody, String username);
}
