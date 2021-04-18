package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_CATEGORIES_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CATEGORY_UPDATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CUSTOM_CATEGORY_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.CUSTOM_CATEGORY_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.DUPLICATE_CATEGORY_NAME_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_CATEGORY_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.CategoryNotFoundException;
import com.budgetmanagementapp.exception.DuplicateCategoryException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.CategoryRequestModel;
import com.budgetmanagementapp.model.CategoryResponseModel;
import com.budgetmanagementapp.model.UpdateCategoryModel;
import com.budgetmanagementapp.repository.CategoryRepository;
import com.budgetmanagementapp.repository.CustomCategoryRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.CategoryService;
import com.budgetmanagementapp.utility.CategoryType;
import com.budgetmanagementapp.utility.CustomValidator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final UserRepository userRepo;
    private final CustomCategoryRepository customCategoryRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public CategoryResponseModel createCustomCategory(CategoryRequestModel requestBody, String username) {
        CustomValidator.validateCategoryRequestModel(requestBody);

        User user = findUserByUsername(username);

        if (customCategoryRepo.byNameAndUser(requestBody.getCategoryName(), user).isPresent()
                || categoryRepo.byName(requestBody.getCategoryName()).isPresent()) {
            throw new DuplicateCategoryException(
                    String.format(DUPLICATE_CATEGORY_NAME_MSG, username, requestBody.getCategoryName()));
        }

        CustomCategory customCategory = buildCustomCategory(requestBody, user);

        log.info(String.format(CUSTOM_CATEGORY_CREATED_MSG,
                user.getUsername(),
                buildCustomCategoryResponseModel(customCategory)));

        return buildCustomCategoryResponseModel(customCategory);
    }

    @Override
    public List<CategoryResponseModel> getCategoriesByUser(String username, boolean includeDefaultCategories) {
        User user = findUserByUsername(username);

        List<CategoryResponseModel> categories = new ArrayList<>();

        if (includeDefaultCategories) {
            categoryRepo.findAll().stream()
                    .map(this::buildCategoryResponseModel)
                    .forEach(categories::add);
        }

        customCategoryRepo.allByUser(user).stream()
                .map(this::buildCustomCategoryResponseModel)
                .forEach(categories::add);

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException(String.format(CUSTOM_CATEGORY_NOT_FOUND_MSG, username));
        }

        categories.sort(Comparator.comparing(CategoryResponseModel::getCategoryName));

        log.info(String.format(ALL_CATEGORIES_MSG, user.getUsername(), categories));
        return categories;
    }

    @Override
    public CategoryResponseModel updateCategory(UpdateCategoryModel requestBody, String username) {
        CustomValidator.validateUpdateCategoryModel(requestBody);
        CustomValidator.validateCategoryType(requestBody.getNewCategoryType());

        CustomCategory category =
                customCategoryRepo.byIdAndUser(requestBody.getCategoryId(), findUserByUsername(username))
                        .orElseThrow(
                                () -> new CategoryNotFoundException(String.format(UNAUTHORIZED_CATEGORY_MSG, username,
                                        requestBody.getCategoryId())));

        category.setIcon(requestBody.getIcon());
        category.setName(requestBody.getNewCategoryName());
        category.setType(CategoryType.valueOf(requestBody.getNewCategoryType().toUpperCase()));
        customCategoryRepo.save(category);

        log.info(String.format(CATEGORY_UPDATED_MSG, username, buildCustomCategoryResponseModel(category)));
        return buildCustomCategoryResponseModel(category);
    }

    private CustomCategory buildCustomCategory(CategoryRequestModel requestModel, User user) {
        CustomValidator.validateCategoryType(requestModel.getCategoryTypeName());

        return customCategoryRepo.save(CustomCategory.builder()
                .customCategoryId(UUID.randomUUID().toString())
                .icon(requestModel.getIcon())
                .name(requestModel.getCategoryName())
                .type(CategoryType.valueOf(requestModel.getCategoryTypeName().toUpperCase()))
                .user(user)
                .build());
    }

    private User findUserByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    private CategoryResponseModel buildCustomCategoryResponseModel(CustomCategory customCategory) {
        return CategoryResponseModel.builder()
                .categoryId(customCategory.getCustomCategoryId())
                .icon(customCategory.getIcon())
                .categoryName(customCategory.getName())
                .categoryType(customCategory.getType())
                .build();
    }

    private CategoryResponseModel buildCategoryResponseModel(Category category) {
        return CategoryResponseModel.builder()
                .categoryId(category.getCategoryId())
                .icon(category.getIcon())
                .categoryName(category.getName())
                .categoryType(category.getType())
                .build();
    }


}
