package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.COMMON_USERNAME;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_CATEGORIES_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CATEGORY_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CATEGORY_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DUPLICATE_CATEGORY_NAME_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_CATEGORY_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.CategoryNotFoundException;
import com.budgetmanagementapp.exception.DuplicateCategoryException;
import com.budgetmanagementapp.model.CategoryRqModel;
import com.budgetmanagementapp.model.CategoryRsModel;
import com.budgetmanagementapp.model.UpdateCategoryRqModel;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.service.CategoryService;
import com.budgetmanagementapp.service.UserService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final UserService userService;
    private final CategoryRepository categoryRepo;

    @Override
    public CategoryRsModel createCategory(CategoryRqModel requestBody, String username) {
        CustomValidator.validateCategoryModel(requestBody);

        User user = userService.findByUsername(username);
        checkDuplicate(requestBody.getCategoryName(), user);
        Category category = buildCategory(requestBody, user);

        log.info(format(CATEGORY_CREATED_MSG, user.getUsername(), buildCategoryResponseModel(category)));
        return buildCategoryResponseModel(category);
    }

    @Override
    public List<CategoryRsModel> getCategoriesByUser(String username, boolean includeCommonCategories) {
        User user = userService.findByUsername(username);

        List<CategoryRsModel> categories = categoriesByUser(includeCommonCategories, user);

        log.info(format(ALL_CATEGORIES_MSG, user.getUsername(), categories));
        return categories;
    }

    @Override
    public CategoryRsModel updateCategory(UpdateCategoryRqModel requestBody, String username) {
        CustomValidator.validateUpdateCategoryModel(requestBody);

        Category category = categoryByIdAndUser(requestBody.getCategoryId(), username);

        updateCategoryValues(requestBody, category);

        log.info(format(CATEGORY_UPDATED_MSG, username, buildCategoryResponseModel(category)));
        return buildCategoryResponseModel(category);
    }

    private Category buildCategory(CategoryRqModel requestModel, User user) {
        CustomValidator.validateCategoryType(requestModel.getCategoryType());

        return categoryRepo.save(Category.builder()
                .categoryId(UUID.randomUUID().toString())
                .icon(requestModel.getIcon())
                .name(requestModel.getCategoryName())
                .type(requestModel.getCategoryType().toUpperCase())
                .user(user)
                .build());
    }

    private CategoryRsModel buildCategoryResponseModel(Category category) {
        return CategoryRsModel.builder()
                .categoryId(category.getCategoryId())
                .icon(category.getIcon())
                .categoryName(category.getName())
                .categoryType(category.getType())
                .build();
    }

    private void updateCategoryValues(UpdateCategoryRqModel requestBody, Category category) {
        CustomValidator.validateCategoryType(requestBody.getCategoryType());

        category.setIcon(requestBody.getIcon());
        category.setName(requestBody.getCategoryName());
        category.setType(requestBody.getCategoryType().toUpperCase());
        categoryRepo.save(category);
    }

    private void checkDuplicate(String categoryName, User user) {
        if (categoryRepo.byNameAndUser(categoryName, user).isPresent()
                || categoryRepo.byNameAndUser(categoryName, userService.findByUsername(COMMON_USERNAME)).isPresent()) {
            throw new DuplicateCategoryException(
                    format(DUPLICATE_CATEGORY_NAME_MSG, user.getUsername(), categoryName));
        }
    }

    private Category categoryByIdAndUser(String categoryId, String username) {
        return categoryRepo.byIdAndUser(categoryId, userService.findByUsername(username))
                .orElseThrow(
                        () -> new CategoryNotFoundException(format(UNAUTHORIZED_CATEGORY_MSG, username, categoryId)));
    }

    private List<CategoryRsModel> categoriesByUser(boolean includeCommonCategories, User user) {
        User generalUser = userService.findByUsername(COMMON_USERNAME);

        return includeCommonCategories
                ? categoryRepo.allByUserOrGeneralUser(user, generalUser)
                .stream()
                .map(this::buildCategoryResponseModel)
                .collect(Collectors.toList())
                : categoryRepo.allByUser(user)
                .stream()
                .map(this::buildCategoryResponseModel)
                .collect(Collectors.toList());
    }

}
