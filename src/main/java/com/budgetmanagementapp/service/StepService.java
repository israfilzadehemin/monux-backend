package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.step.StepRqModel;
import com.budgetmanagementapp.model.step.StepRsModel;
import com.budgetmanagementapp.model.step.UpdateStepRqModel;

import java.util.List;

public interface StepService {

    List<StepRsModel> getAllSteps(String language);

    StepRsModel createStep(StepRqModel request);

    StepRsModel updateStep(UpdateStepRqModel request);

    StepRsModel deleteStep(String stepId);
}
