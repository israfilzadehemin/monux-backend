package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Currency;
import com.budgetmanagementapp.entity.CustomCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {
}
