package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;

import java.util.List;

public interface FeatureService {
    List<FeatureRsModel> getAllFeatures(String language);

    FeatureRsModel addFeature(FeatureRqModel request);

    FeatureRsModel updateFeature(FeatureRqModel request, String featureId);

    FeatureRsModel deleteFeature(String featureId);
}
