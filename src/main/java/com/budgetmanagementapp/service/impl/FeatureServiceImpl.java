package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.Plan;
import com.budgetmanagementapp.exception.FeatureNotFoundException;
import com.budgetmanagementapp.mapper.FeatureMapper;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import com.budgetmanagementapp.model.feature.UpdateFeatureRqModel;
import com.budgetmanagementapp.repository.FeatureRepository;
import com.budgetmanagementapp.service.FeatureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepo;

    @Override
    public List<FeatureRsModel> getAllFeatures() {
        return featureRepo.findAll().stream()
                .map(FeatureMapper.INSTANCE::buildFeatureResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public FeatureRsModel addFeature(FeatureRqModel request) {
        Feature feature = FeatureMapper.INSTANCE.buildFeature(request);
        featureRepo.save(feature);
        FeatureRsModel response = FeatureMapper.INSTANCE.buildFeatureResponseModel(feature);
        log.info(format(FEATURE_CREATED_MSG, response));
        return response;
    }

    @Override
    public FeatureRsModel updateFeature(UpdateFeatureRqModel request) {
        Feature feature = findById(request.getFeatureId());
        feature.setContent(request.getContent());
        featureRepo.save(feature);
        FeatureRsModel response = FeatureMapper.INSTANCE.buildFeatureResponseModel(feature);
        log.info(format(FEATURE_UPDATED_MSG, response));
        return response;
    }

    @Override
    public FeatureRsModel deleteFeature(String featureId) {
        Feature feature = findById(featureId);
        featureRepo.delete(feature);
        FeatureRsModel response = FeatureMapper.INSTANCE.buildFeatureResponseModel(feature);
        log.info(format(FEATURE_DELETED_MSG, response));
        return response;
    }

    private Feature findById(String featureId) {
        return featureRepo.byFeatureId(featureId).orElseThrow(
                () -> new FeatureNotFoundException(format(FEATURE_NOT_FOUND_MSG, featureId))
        );
    }
}
