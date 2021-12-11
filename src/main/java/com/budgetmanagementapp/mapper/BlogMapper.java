package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Blog;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.utility.CustomFormatter;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public abstract class BlogMapper {

    public static BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

    @Mappings({
            @Mapping(target = "textAz", source = "text.az"),
            @Mapping(target = "textEn", source = "text.en"),
            @Mapping(target = "textRu", source = "text.ru"),
            @Mapping(target = "titleAz", source = "title.az"),
            @Mapping(target = "titleEn", source = "title.en"),
            @Mapping(target = "titleRu", source = "title.ru")
    })
    public abstract BlogRsModel buildBlogResponseModel(Blog blog);

    @Mappings({
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "text", ignore = true),
            @Mapping(target = "title.az", source = "titleAz"),
            @Mapping(target = "title.en", source = "titleEn"),
            @Mapping(target = "title.ru", source = "titleRu"),
            @Mapping(target = "text.az", source = "textAz"),
            @Mapping(target = "text.en", source = "textEn"),
            @Mapping(target = "text.ru", source = "textRu")
    })
    public abstract Blog buildBlog(BlogRqModel request);

    @AfterMapping
    void mapDateTimeAndId(@MappingTarget Blog.BlogBuilder blog, BlogRqModel request) {
        blog.title(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getTitleAz())
                .en(request.getTitleEn())
                .ru(request.getTitleRu())
                .build());
        blog.text(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getTextAz())
                .en(request.getTextEn())
                .ru(request.getTextRu())
                .build());
        blog.blogId(UUID.randomUUID().toString());
        blog.creationDate(CustomFormatter.stringToLocalDateTime(request.getCreationDate()));
        blog.updateDate(CustomFormatter.stringToLocalDateTime(request.getUpdateDate()));
    }

}