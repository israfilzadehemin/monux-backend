package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.entity.Plan;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public abstract class PlanMapper {

    public static final PlanMapper PLAN_MAPPER_INSTANCE = Mappers.getMapper(PlanMapper.class);

    @Mappings({
            @Mapping(target = "title.az", source = "request.titleAz"),
            @Mapping(target = "title.en", source = "request.titleEn"),
            @Mapping(target = "title.ru", source = "request.titleRu"),
            @Mapping(target = "text.az", source = "request.textAz"),
            @Mapping(target = "text.en", source = "request.textEn"),
            @Mapping(target = "text.ru", source = "request.textRu"),
            @Mapping(target = "features", ignore = true)
    })

    public abstract Plan buildPlan(PlanRqModel request, List<Feature> features);

    @AfterMapping
    void setLanguageBasedFieldsToResponseModel(@MappingTarget Plan.PlanBuilder plan, List<Feature> features, PlanRqModel request) {
        plan.planId(UUID.randomUUID().toString());
        plan.features(features);

        plan.title(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getTitleAz())
                .en(request.getTitleEn())
                .ru(request.getTitleRu())
                .build());
        plan.text(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getTextAz())
                .en(request.getTextEn())
                .ru(request.getTextRu())
                .build());
    }

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract PlanRsModel buildPlanResponseModel(Plan plan);

    @AfterMapping
    void setLanguageBasedFieldsToResponseModel(@MappingTarget PlanRsModel.PlanRsModelBuilder response, Plan plan) {
        response.featuresIds(plan.getFeatures().stream()
                .map(Feature::getFeatureId)
                .collect(Collectors.toList()));

        Map<String, String> titles = new HashMap<>();
        Map<String, String> texts = new HashMap<>();

        titles.put("titleAz", plan.getTitle().getAz());
        titles.put("titleEn", plan.getTitle().getEn());
        titles.put("titleRu", plan.getTitle().getRu());

        texts.put("textAz", plan.getText().getAz());
        texts.put("textEn", plan.getText().getEn());
        texts.put("textRu", plan.getText().getRu());

        response.title(titles);
        response.text(texts);
    }

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract PlanRsModel buildPlanResponseModelWithLanguage(Plan plan, String language);

    @AfterMapping
    void mapLanguageBasedFields(@MappingTarget PlanRsModel.PlanRsModelBuilder response, Plan plan, String language) {
        response.featuresIds(plan.getFeatures().stream()
                .map(Feature::getFeatureId)
                .collect(Collectors.toList()));

        switch (language) {
            case "en" -> {
                response.title(plan.getTitle().getEn());
                response.text(plan.getText().getEn());
            }
            case "ru" -> {
                response.title(plan.getTitle().getRu());
                response.text(plan.getText().getRu());
            }
            default -> {
                response.title(plan.getTitle().getAz());
                response.text(plan.getText().getAz());
            }
        }
    }
}
