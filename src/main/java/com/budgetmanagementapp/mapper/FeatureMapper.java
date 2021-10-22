package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class FeatureMapper {

    public static FeatureMapper INSTANCE = Mappers.getMapper(FeatureMapper.class);

    public abstract Feature buildFeature(FeatureRqModel request);

    @AfterMapping
    void mapFeatureId(@MappingTarget Feature.FeatureBuilder feature){
        feature.featureId(UUID.randomUUID().toString());
    }

    public abstract FeatureRsModel buildFeatureResponseModel(Feature feature);

}
