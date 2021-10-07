package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.builder.BlogBuilder;
import com.budgetmanagementapp.entity.Blog;
import com.budgetmanagementapp.exception.BlogNotFoundException;
import com.budgetmanagementapp.mapper.BlogMapper;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.model.blog.UpdateBlogRqModel;
import com.budgetmanagementapp.repository.BlogRepository;
import com.budgetmanagementapp.service.BlogService;
import com.budgetmanagementapp.utility.CustomFormatter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepo;
    private final BlogBuilder blogBuilder;

    @Override
    public List<BlogRsModel> getAllBlogs() {
        return blogRepo.findAll().stream()
                .map(BlogMapper.INSTANCE::buildBlogResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public BlogRsModel getBlogById(String blogId) {
        return BlogMapper.INSTANCE.buildBlogResponseModel(blogById(blogId));
    }

    @Override
    public Blog getBlogByDate(String dateTime) {
        return blogRepo.findByCreationDate(CustomFormatter.stringToLocalDateTime(dateTime))
                .orElseThrow( () -> new BlogNotFoundException(format(BLOG_NOT_FOUND_MSG, dateTime)));
    }

    @Override
    public BlogRsModel addBlog(BlogRqModel request) {
        Blog blog = blogBuilder.buildBlog(request);
        blogRepo.save(blog);
        BlogRsModel response = BlogMapper.INSTANCE.buildBlogResponseModel(blog);
        log.info(format(BLOG_CREATED_MSG, response));
        return response;
    }

    @Override
    public BlogRsModel updateBlog(UpdateBlogRqModel request) {
        Blog blog = blogById(request.getBlogId());
        blog.setTitle(request.getTitle());
        blog.setText(request.getText());
        blog.setIcon(request.getIcon());
        blog.setUpdateDate(CustomFormatter.stringToLocalDateTime(request.getUpdateDate()));
        blogRepo.save(blog);
        BlogRsModel response = BlogMapper.INSTANCE.buildBlogResponseModel(blog);
        log.info(format(BLOG_UPDATED_MSG, response));
        return response;
    }

    @Override
    public BlogRsModel deleteBlog(String blogId) {
        Blog blog = blogById(blogId);
        blogRepo.delete(blog);
        BlogRsModel response = BlogMapper.INSTANCE.buildBlogResponseModel(blog);
        log.info(format(BLOG_DELETED_MSG, response));
        return response;
    }

    public Blog blogById(String blogId) {
        return blogRepo.byBlogId(blogId)
                .orElseThrow( () -> new BlogNotFoundException(format(BLOG_NOT_FOUND_MSG, blogId)));
    }

}
