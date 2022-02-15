package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;

import java.util.List;

public interface PlanService {
    List<PlanRsModel> getAllPlans(String language);

    PlanRsModel addPlan(PlanRqModel request);

    PlanRsModel updatePlan(PlanRqModel request, String planId);

    PlanRsModel deletePlan(String planId);

}
