package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.home.StepRqModel;
import com.budgetmanagementapp.model.home.StepRsModel;

import java.util.List;

public interface StepService {

    List<StepRsModel> getAllSteps();

    StepRsModel createStep(StepRqModel request);

    StepRsModel updateStep(StepRqModel request, String stepId);

    StepRsModel deleteStep(String stepId);
}
