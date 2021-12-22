package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class FeedbackMapper {

    public static FeedbackMapper FEEDBACK_MAPPER_INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    @Mapping(source = "feedback.dateTime", target = "creationDateTime")
    public abstract FeedbackRsModel buildFeedbackResponseModel(Feedback feedback);
}