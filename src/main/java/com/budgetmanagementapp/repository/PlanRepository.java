package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    default Optional<Plan> byPlanId(String planId){
        return findByPlanId(planId);
    }

    Optional<Plan> findByPlanId(String planId);
}
