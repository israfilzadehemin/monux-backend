package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.entity.Plan;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class PlanMapper {

    public static PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);

    @Mapping(target = "features", ignore = true)
    public abstract Plan buildPlan(PlanRqModel request, List<Feature> features);

    @AfterMapping
    void mapPlanIdAndPeriodType(@MappingTarget Plan.PlanBuilder plan, List<Feature> features) {
        plan.planId(UUID.randomUUID().toString());
        plan.features(features);
    }

    public abstract PlanRsModel buildPlanResponseModel(Plan plan);

    @AfterMapping
    void mapFeatureIds(@MappingTarget PlanRsModel.PlanRsModelBuilder planRsModel, Plan plan) {
        planRsModel.featuresIds(plan.getFeatures().stream()
                .map(Feature::getFeatureId)
                .collect(Collectors.toList()));
    }
}
