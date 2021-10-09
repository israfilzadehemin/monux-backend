package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;

import java.util.List;

public interface FeatureService {
    List<FeatureRsModel> getAllFeatures();

    FeatureRsModel addFeature(FeatureRqModel request);
}
