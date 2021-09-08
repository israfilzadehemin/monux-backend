package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    @Mapping(source = "feedback.dateTime", target = "creationDateTime")
    FeedbackRsModel buildFeedbackResponseModel(Feedback feedback);
}