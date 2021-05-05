package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.CategoryRqModel;
import com.budgetmanagementapp.model.CategoryRsModel;
import com.budgetmanagementapp.model.UpdateCategoryRqModel;
import java.util.List;

public interface CategoryService {

    CategoryRsModel createCategory(CategoryRqModel requestBody, String username);

    List<CategoryRsModel> getCategoriesByUser(String username, boolean includeCommonCategories);

    CategoryRsModel updateCategory(UpdateCategoryRqModel requestBody, String username);
}
