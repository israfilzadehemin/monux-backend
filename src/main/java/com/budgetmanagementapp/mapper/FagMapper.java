package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Fag;
import com.budgetmanagementapp.model.home.FagRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class FagMapper {
    public static FagMapper INSTANCE = Mappers.getMapper(FagMapper.class);

    public abstract FagRsModel buildFagResponseModel(Fag fag);
}
