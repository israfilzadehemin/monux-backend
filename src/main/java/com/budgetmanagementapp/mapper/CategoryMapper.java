package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.model.category.CategoryRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    public static CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mappings({
            @Mapping(source = "category.name", target = "categoryName"),
            @Mapping(source = "category.type", target = "categoryType"),
    })
    public abstract CategoryRsModel buildCategoryResponseModel(Category category);
}