package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.mapper.FeatureMapper;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import com.budgetmanagementapp.repository.FeatureRepository;
import com.budgetmanagementapp.service.FeatureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.MsgConstant.FEATURE_CREATED_MSG;
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
}
