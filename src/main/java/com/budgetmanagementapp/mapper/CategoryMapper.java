package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.CategoryRqModel;
import com.budgetmanagementapp.model.CategoryRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "categoryName"),
            @Mapping(source = "type", target = "categoryType"),
    })
    CategoryRsModel buildCategoryResponseModel(Category category);

    @Mappings({
            @Mapping(source = "requestBody.categoryName", target = "name"),
            @Mapping(target = "type", expression = "java(requestBody.getCategoryType().toUpperCase())"),
            @Mapping(target = "categoryId", expression = "java(generateUuid())")
    })
    Category buildCategory(CategoryRqModel requestBody, User user);

    default String generateUuid() {
        return UUID.randomUUID().toString();
    }
}