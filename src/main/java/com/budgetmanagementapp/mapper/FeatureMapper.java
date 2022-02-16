package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.feature.FeatureRqModel;
import com.budgetmanagementapp.model.feature.FeatureRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper
public abstract class FeatureMapper {

    public static FeatureMapper FEATURE_MAPPER_INSTANCE = Mappers.getMapper(FeatureMapper.class);

    @Mappings({
            @Mapping(target = "content.az", source = "contentAz"),
            @Mapping(target = "content.en", source = "contentEn"),
            @Mapping(target = "content.ru", source = "contentRu"),
    })
    public abstract Feature buildFeature(FeatureRqModel request);

    @AfterMapping
    void setExtraFields(@MappingTarget Feature.FeatureBuilder feature, FeatureRqModel request) {
        feature.featureId(UUID.randomUUID().toString());

        feature.content(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getContentAz())
                .en(request.getContentEn())
                .ru(request.getContentRu())
                .build());
    }

    @Mapping(target = "content", ignore = true)
    public abstract FeatureRsModel buildFeatureResponseModel(Feature feature);

    @AfterMapping
    void setLanguageValuesToResponseModel(@MappingTarget FeatureRsModel.FeatureRsModelBuilder response, Feature feature) {
        Map<String, String> contents = new HashMap<>();

        contents.put("contentAz", feature.getContent().getAz());
        contents.put("contentEn", feature.getContent().getEn());
        contents.put("contentRu", feature.getContent().getRu());

        response.content(contents);
    }

    @Mapping(target = "content", ignore = true)
    public abstract FeatureRsModel buildFeatureResponseModelWithLanguage(Feature feature, String language);

    @AfterMapping
    void mapLanguageBasedFields(@MappingTarget FeatureRsModel.FeatureRsModelBuilder response, Feature feature, String language) {
        switch (language) {
            case "en" -> response.content(feature.getContent().getEn());
            case "ru" -> response.content(feature.getContent().getRu());
            default -> response.content(feature.getContent().getAz());
        }
    }


}
