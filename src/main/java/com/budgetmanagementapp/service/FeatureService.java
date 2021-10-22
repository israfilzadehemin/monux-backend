package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import com.budgetmanagementapp.model.feature.UpdateFeatureRqModel;

import java.util.List;

public interface FeatureService {
    List<FeatureRsModel> getAllFeatures();

    FeatureRsModel addFeature(FeatureRqModel request);

    FeatureRsModel updateFeature(UpdateFeatureRqModel request);

    FeatureRsModel deleteFeature(String featureId);
}
