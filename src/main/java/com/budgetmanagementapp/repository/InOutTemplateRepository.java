package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.entity.InOutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InOutTemplateRepository extends JpaRepository<InOutTemplate, Long> {
}
