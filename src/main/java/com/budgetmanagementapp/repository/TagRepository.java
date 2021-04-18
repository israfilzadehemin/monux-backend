package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
