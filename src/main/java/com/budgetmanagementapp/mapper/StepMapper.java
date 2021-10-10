package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Step;
import com.budgetmanagementapp.model.home.StepRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class StepMapper {

    public static StepMapper INSTANCE = Mappers.getMapper(StepMapper.class);

    public abstract StepRsModel buildStepResponseModel(Step step);

}
