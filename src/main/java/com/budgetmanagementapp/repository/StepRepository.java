package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
