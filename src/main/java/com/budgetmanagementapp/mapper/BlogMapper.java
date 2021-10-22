package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Blog;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.utility.CustomFormatter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class BlogMapper {

    public static BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

    public abstract BlogRsModel buildBlogResponseModel(Blog blog);

    @Mappings({
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
    })
    public abstract Blog buildBlog(BlogRqModel request);

    @AfterMapping
    void mapDateTimeAndId(@MappingTarget Blog.BlogBuilder blog, BlogRqModel request) {
        blog.blogId(UUID.randomUUID().toString());
        blog.creationDate(CustomFormatter.stringToLocalDateTime(request.getCreationDate()));
        blog.updateDate(CustomFormatter.stringToLocalDateTime(request.getUpdateDate()));
    }

}