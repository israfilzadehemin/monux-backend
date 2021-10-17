package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Definition;
import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class DefinitionMapper {

    public static DefinitionMapper INSTANCE = Mappers.getMapper(DefinitionMapper.class);

    public abstract Definition buildDefinition(DefinitionRqModel request);

    @AfterMapping
    void mapDefinitionId(@MappingTarget Definition.DefinitionBuilder definition) {
        definition.definitionId(UUID.randomUUID().toString());
    }

    public abstract DefinitionRsModel buildDefinitionRsModel(Definition definition);
}
