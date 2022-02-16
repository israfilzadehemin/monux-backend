package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.step.StepRqModel;
import com.budgetmanagementapp.model.step.StepRsModel;

import java.util.List;

public interface StepService {

    List<StepRsModel> getAllSteps(String language);

    StepRsModel createStep(StepRqModel request);

    StepRsModel updateStep(StepRqModel request, String stepId);

    StepRsModel deleteStep(String stepId);
}
