package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    default Optional<Blog> byCreationDate(LocalDateTime creationDate){
        return findByCreationDate(creationDate);
    }

    default Optional<Blog> byBlogId(String blogId){
        return findBlogByBlogId(blogId);
    }

    Optional<Blog> findByCreationDate(LocalDateTime creationDate);

    Optional<Blog> findBlogByBlogId(String blogId);
}