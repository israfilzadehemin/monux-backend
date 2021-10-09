package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import com.budgetmanagementapp.model.plan.UpdatePlanRqModel;

import java.util.List;

public interface PlanService {
    List<PlanRsModel> getAllPlans();

    PlanRsModel addPlan(PlanRqModel request);

    PlanRsModel updatePlan(UpdatePlanRqModel request);

    PlanRsModel deletePlan(String planId);

}
