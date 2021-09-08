package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Template;
import com.budgetmanagementapp.model.transaction.TransactionRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);

    @Mapping(source = "templateId", target = "transactionId")
    TransactionRsModel buildGenericResponseModel(Template template);
}
