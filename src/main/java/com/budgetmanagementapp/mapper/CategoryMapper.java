package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.model.category.CategoryRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mappings({
            @Mapping(source = "category.name", target = "categoryName"),
            @Mapping(source = "category.type", target = "categoryType"),
    })
    CategoryRsModel buildCategoryResponseModel(Category category);
}