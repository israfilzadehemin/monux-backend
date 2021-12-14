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
            @Mapping(target = "text", ignore = true),
            @Mapping(target = "title", ignore = true)
    })
    public abstract BlogRsModel buildBlogResponseModel(Blog blog);

    @Mappings({
            @Mapping(target = "text", ignore = true),
            @Mapping(target = "title", ignore = true)
    })
    public abstract BlogRsModel buildBlogResponseModelWithLanguage(Blog blog, String language);

    @AfterMapping
    void mapBlogToResponse(@MappingTarget BlogRsModel.BlogRsModelBuilder response, Blog blog, String language) {
        switch (language) {
            case "en" -> {
                response.title(blog.getTitle().getEn());
                response.text(blog.getText().getEn());
            }
            case "ru" -> {
                response.title(blog.getTitle().getRu());
                response.text(blog.getText().getRu());
            }
            default -> {
                response.title(blog.getTitle().getAz());
                response.text(blog.getText().getAz());
            }
        }
    }

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
    void mapBlogToResponse(@MappingTarget Blog.BlogBuilder blog, BlogRqModel request) {
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