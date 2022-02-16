package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.blog.BlogRqModel;
import com.budgetmanagementapp.model.blog.BlogRsModel;

import java.util.List;

public interface BlogService {

    List<BlogRsModel> getAllBlogs(String language);

    BlogRsModel getBlogById(String blogId, String language);

    BlogRsModel addBlog(BlogRqModel request);

    BlogRsModel updateBlog(BlogRqModel request, String blogId);

    BlogRsModel deleteBlog(String blogId);
}
