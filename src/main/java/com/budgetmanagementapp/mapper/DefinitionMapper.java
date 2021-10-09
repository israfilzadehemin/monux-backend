package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Definition;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class DefinitionMapper {

    public static DefinitionMapper INSTANCE = Mappers.getMapper(DefinitionMapper.class);

    public abstract DefinitionRsModel buildDefinitionRsModel(Definition definition);
}
