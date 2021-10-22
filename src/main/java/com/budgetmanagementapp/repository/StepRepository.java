package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long> {

    default Optional<Step> byId(String stepId) {
        return findByStepId(stepId);
    }

    Optional<Step> findByStepId(String stepId);
}
