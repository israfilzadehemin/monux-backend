package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Blog;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BlogMapper {

    public static BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

    public abstract BlogRsModel buildBlogResponseModel(Blog blog);
}