package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.builder.CategoryBuilder;
import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.exception.DuplicateException;
import com.budgetmanagementapp.model.category.CategoryRqModel;
import com.budgetmanagementapp.model.category.CategoryRsModel;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.service.CategoryService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.CustomValidator;
import com.budgetmanagementapp.utility.TransactionType;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.mapper.CategoryMapper.CATEGORY_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.Constant.COMMON_USERNAME;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Service
@Log4j2
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final UserService userService;
    private final CategoryRepository categoryRepo;
    private final CategoryBuilder categoryBuilder;

    @Override
    public CategoryRsModel createCategory(CategoryRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        checkDuplicate(requestBody.getCategoryName(), user);
        Category category = categoryRepo.save(categoryBuilder.buildCategory(requestBody, user));

        log.info(CATEGORY_CREATED_MSG,
                user.getUsername(), CATEGORY_MAPPER_INSTANCE.buildCategoryResponseModel(category));
        return CATEGORY_MAPPER_INSTANCE.buildCategoryResponseModel(category);
    }

    @Override
    public List<CategoryRsModel> getCategoriesByUser(String username, boolean includeCommonCategories) {
        User user = userService.findByUsername(username);
        List<CategoryRsModel> categories = categoriesByUser(includeCommonCategories, user);

        log.info(ALL_CATEGORIES_MSG, user.getUsername(), categories);
        return categories;
    }

    @Override
    public CategoryRsModel updateCategory(CategoryRqModel requestBody, String categoryId, String username) {
        Category category = categoryByIdAndUser(categoryId, username);
        updateCategoryValues(requestBody, category);

        var categoryRsModel = CATEGORY_MAPPER_INSTANCE.buildCategoryResponseModel(category);

        log.info(CATEGORY_UPDATED_MSG, username, categoryRsModel);
        return categoryRsModel;
    }

    @Override
    public Category byIdAndTypeAndUser(String categoryId, TransactionType type, User user) {
        var category = categoryRepo
                .byIdAndTypeAndUsers(
                        categoryId,
                        CategoryType.valueOf(type.name()).name(),
                        Arrays.asList(user, userService.findByUsername(COMMON_USERNAME)))
                .orElseThrow(() -> new DataNotFoundException(format(INVALID_CATEGORY_ID_MSG, categoryId), 4000));

        log.info(CATEGORY_BY_ID_TYPE_USER_MSG, categoryId, type, user, category);
        return category;
    }

    private void updateCategoryValues(CategoryRqModel requestBody, Category category) {
        CustomValidator.validateCategoryType(requestBody.getCategoryType());

        category.setIcon(requestBody.getIcon());
        category.setName(requestBody.getCategoryName());
        category.setType(requestBody.getCategoryType().toUpperCase());
        categoryRepo.save(category);
    }

    private void checkDuplicate(String categoryName, User user) {
        if (categoryRepo.byNameAndUser(categoryName, user).isPresent()
                || categoryRepo.byNameAndUser(categoryName, userService.findByUsername(COMMON_USERNAME)).isPresent()) {
            throw new DuplicateException(
                    format(DUPLICATE_CATEGORY_NAME_MSG, user.getUsername(), categoryName), 4002);
        }
    }

    private Category categoryByIdAndUser(String categoryId, String username) {
        return categoryRepo.byIdAndUser(categoryId, userService.findByUsername(username))
                .orElseThrow(
                        () -> new DataNotFoundException(format(UNAUTHORIZED_CATEGORY_MSG, username, categoryId), 4000));
    }

    private List<CategoryRsModel> categoriesByUser(boolean includeCommonCategories, User user) {
        User commonUser = userService.findByUsername(COMMON_USERNAME);

        return includeCommonCategories
                ? categoryRepo.allByUserOrGeneralUser(user, commonUser)
                .stream()
                .map(CATEGORY_MAPPER_INSTANCE::buildCategoryResponseModel)
                .collect(Collectors.toList())
                : categoryRepo.allByUser(user)
                .stream()
                .map(CATEGORY_MAPPER_INSTANCE::buildCategoryResponseModel)
                .collect(Collectors.toList());
    }

}
