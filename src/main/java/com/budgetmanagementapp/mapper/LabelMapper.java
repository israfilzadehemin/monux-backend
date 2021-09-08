package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.model.label.LabelRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LabelMapper {

    LabelMapper INSTANCE = Mappers.getMapper(LabelMapper.class);

    @Mappings({
            @Mapping(source = "label.name", target = "labelName"),
            @Mapping(source = "label.type", target = "labelCategory")
    })
    LabelRsModel buildLabelResponseModel(Label label);
}