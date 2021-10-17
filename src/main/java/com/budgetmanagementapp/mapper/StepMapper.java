package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Step;
import com.budgetmanagementapp.model.home.StepRqModel;
import com.budgetmanagementapp.model.home.StepRsModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class StepMapper {

    public static StepMapper INSTANCE = Mappers.getMapper(StepMapper.class);

    public abstract Step buildStep(StepRqModel request);

    @AfterMapping
    void mapStepId(@MappingTarget Step.StepBuilder step) {
        step.stepId(UUID.randomUUID().toString());
    }

    public abstract StepRsModel buildStepResponseModel(Step step);

}
