package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.category.CategoryRqModel;
import com.budgetmanagementapp.model.category.CategoryRsModel;
import com.budgetmanagementapp.utility.TransactionType;

import java.util.List;

public interface CategoryService {

    CategoryRsModel createCategory(CategoryRqModel requestBody, String username);

    List<CategoryRsModel> getCategoriesByUser(String username, boolean includeCommonCategories);

    CategoryRsModel updateCategory(CategoryRqModel requestBody, String categoryId, String username);

    Category byIdAndTypeAndUser(String categoryId, TransactionType type, User user);
}
