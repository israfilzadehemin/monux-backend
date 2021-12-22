package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.model.label.LabelRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class LabelMapper {

    public static final LabelMapper LABEL_MAPPER_INSTANCE = Mappers.getMapper(LabelMapper.class);

    @Mappings({
            @Mapping(source = "label.name", target = "labelName"),
            @Mapping(source = "label.type", target = "labelCategory")
    })
    public abstract LabelRsModel buildLabelResponseModel(Label label);
}