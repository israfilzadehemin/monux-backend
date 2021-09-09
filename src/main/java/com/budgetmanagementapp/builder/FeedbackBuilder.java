package com.budgetmanagementapp.builder;

import com.budgetmanagementapp.entity.*;
import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.budgetmanagementapp.utility.Constant.STATUS_OPEN;

@AllArgsConstructor
@Component
public class FeedbackBuilder {

    private final FeedbackRepository feedbackRepo;

    public Feedback buildFeedback(FeedbackRqModel requestBody, User user) {
        return feedbackRepo.save(Feedback.builder()
                .feedbackId(UUID.randomUUID().toString())
                .description(requestBody.getDescription())
                .dateTime(LocalDateTime.now())
                .status(STATUS_OPEN)
                .user(user)
                .build());
    }


}
