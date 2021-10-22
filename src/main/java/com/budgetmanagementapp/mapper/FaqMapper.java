package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Faq;
import com.budgetmanagementapp.model.faq.FaqRqModel;
import com.budgetmanagementapp.model.faq.FaqRsModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class FaqMapper {

    public static FaqMapper INSTANCE = Mappers.getMapper(FaqMapper.class);

    public abstract Faq buildFaq(FaqRqModel request);

    @AfterMapping
    void mapFaqId(@MappingTarget Faq.FaqBuilder faq) {
        faq.faqId(UUID.randomUUID().toString());
    }

    public abstract FaqRsModel buildFaqResponseModel(Faq faq);
}
