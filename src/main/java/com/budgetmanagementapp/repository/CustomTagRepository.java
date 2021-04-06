package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.CustomNotification;
import com.budgetmanagementapp.entity.CustomTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomTagRepository extends JpaRepository<CustomTag, Long> {
}
