package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
