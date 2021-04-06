package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.DebtTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtTemplateRepository extends JpaRepository<DebtTemplate, Long> {
}
