package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.Blog;
import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;
import com.budgetmanagementapp.model.blog.UpdateBlogRqModel;

import java.util.List;

public interface BlogService {

    List<BlogRsModel> getAllBlogs();

    BlogRsModel getBlogById(String blogId);

    Blog getBlogByDate(String dateTime);

    BlogRsModel addBlog(BlogRqModel request);

    BlogRsModel updateBlog(UpdateBlogRqModel request);

    BlogRsModel deleteBlog(String blogId);
}