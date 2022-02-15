package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Blog;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.repository.BlogRepository;
import com.budgetmanagementapp.service.BlogService;
import com.budgetmanagementapp.utility.CustomFormatter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.mapper.BlogMapper.BLOG_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepo;

    @Override
    public List<BlogRsModel> getAllBlogs(String language) {
        var allBlogs = blogRepo.findAll()
                .stream()
                .map(blog -> BLOG_MAPPER_INSTANCE.buildBlogResponseModelWithLanguage(blog, language))
                .collect(Collectors.toList());

        log.info(ALL_BLOGS_MSG, allBlogs);
        return allBlogs;
    }

    @Override
    public BlogRsModel getBlogById(String blogId, String language) {
        var blog = BLOG_MAPPER_INSTANCE.buildBlogResponseModelWithLanguage(blogById(blogId), language);

        log.info(BLOG_BY_ID_MSG, blogId, blog);
        return blog;
    }

    @Override
    public BlogRsModel addBlog(BlogRqModel request) {
        Blog blog = BLOG_MAPPER_INSTANCE.buildBlog(request);
        blogRepo.save(blog);

        BlogRsModel response = BLOG_MAPPER_INSTANCE.buildBlogResponseModel(blog);

        log.info(BLOG_CREATED_MSG, response);
        return response;
    }

    @Override
    public BlogRsModel updateBlog(BlogRqModel request, String blogId) {
        Blog blog = blogById(blogId);
        blog.setTitle(Translation.builder()
                .az(request.getTitleAz()).en(request.getTitleEn()).ru(request.getTitleRu())
                .build());

        blog.setText(Translation.builder()
                .az(request.getTextAz()).en(request.getTextEn()).ru(request.getTextRu())
                .build());

        blog.setImage(request.getImage());
        blog.setUpdateDate(CustomFormatter.stringToLocalDateTime(request.getUpdateDate()));
        blogRepo.save(blog);

        BlogRsModel response = BLOG_MAPPER_INSTANCE.buildBlogResponseModel(blog);
        log.info(BLOG_UPDATED_MSG, response);
        return response;
    }

    @Override
    public BlogRsModel deleteBlog(String blogId) {
        Blog blog = blogById(blogId);
        blogRepo.delete(blog);

        BlogRsModel response = BLOG_MAPPER_INSTANCE.buildBlogResponseModel(blog);

        log.info(BLOG_DELETED_MSG, response);
        return response;
    }

    public Blog blogById(String blogId) {
        return blogRepo.byBlogId(blogId)
                .orElseThrow(() -> new DataNotFoundException(format(BLOG_NOT_FOUND_MSG, blogId), 6001));
    }

}
