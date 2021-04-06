package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.AccountType;
import com.budgetmanagementapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
