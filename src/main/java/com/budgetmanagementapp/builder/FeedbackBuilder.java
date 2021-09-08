package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.model.FeedbackRqModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.budgetmanagementapp.utility.Constant.STATUS_OPEN;

@Component
public class FeedbackBuilder {
    public Feedback buildFeedback(FeedbackRqModel requestBody, User user) {
        return Feedback.builder()
                .feedbackId(UUID.randomUUID().toString())
                .description(requestBody.getDescription())
                .dateTime(LocalDateTime.now())
                .status(STATUS_OPEN)
                .user(user)
                .build();
    }


}
