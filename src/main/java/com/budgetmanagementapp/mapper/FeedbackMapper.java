package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.model.CategoryRsModel;
import com.budgetmanagementapp.model.FeedbackRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    @Mapping(source = "dateTime", target = "creationDateTime")
    FeedbackRsModel buildFeedbackResponseModel(Feedback feedback);
}