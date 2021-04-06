package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.DebtTransaction;
import com.budgetmanagementapp.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
