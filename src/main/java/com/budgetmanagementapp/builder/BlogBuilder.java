package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Blog;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.utility.CustomFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class BlogBuilder {

    public Blog buildBlog(BlogRqModel request) {
        return Blog.builder()
                .blogId(UUID.randomUUID().toString())
                .title(request.getTitle())
                .text(request.getText())
                .icon(request.getIcon())
                .creationDate(CustomFormatter.stringToLocalDateTime(request.getCreationDate()))
                .updateDate(CustomFormatter.stringToLocalDateTime(request.getUpdateDate()))
                .build();
    }
}
