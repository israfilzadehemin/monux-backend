package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.budgetmanagementapp.utility.Constant.STATUS_OPEN;

@AllArgsConstructor
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
