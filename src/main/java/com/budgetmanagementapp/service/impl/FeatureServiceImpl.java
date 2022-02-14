package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.DataNotFoundException;
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

import static com.budgetmanagementapp.mapper.FeatureMapper.FEATURE_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepo;

    @Override
    public List<FeatureRsModel> getAllFeatures(String language) {
        var features = featureRepo.findAll().stream()
                .map(feature -> FEATURE_MAPPER_INSTANCE.buildFeatureResponseModelWithLanguage(feature, language))
                .collect(Collectors.toList());

        log.info(ALL_FEATURES_MSG, features);
        return features;
    }

    @Override
    public FeatureRsModel addFeature(FeatureRqModel request) {
        Feature feature = FEATURE_MAPPER_INSTANCE.buildFeature(request);
        featureRepo.save(feature);

        FeatureRsModel response = FEATURE_MAPPER_INSTANCE.buildFeatureResponseModel(feature);

        log.info(FEATURE_CREATED_MSG, response);
        return response;
    }

    @Override
    public FeatureRsModel updateFeature(UpdateFeatureRqModel request) {
        Feature feature = findById(request.getFeatureId());
        feature.setContent(Translation.builder()
                .az(request.getContentAz()).en(request.getContentEn()).ru(request.getContentRu())
                .build());
        featureRepo.save(feature);

        FeatureRsModel response = FEATURE_MAPPER_INSTANCE.buildFeatureResponseModel(feature);

        log.info(FEATURE_UPDATED_MSG, response);
        return response;
    }

    @Override
    public FeatureRsModel deleteFeature(String featureId) {
        Feature feature = findById(featureId);
        featureRepo.delete(feature);

        FeatureRsModel response = FEATURE_MAPPER_INSTANCE.buildFeatureResponseModel(feature);

        log.info(FEATURE_DELETED_MSG, response);
        return response;
    }

    private Feature findById(String featureId) {
        return featureRepo.byFeatureId(featureId).orElseThrow(
                () -> new DataNotFoundException(format(FEATURE_NOT_FOUND_MSG, featureId), 6004)
        );
    }
}
