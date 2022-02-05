package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.builder.FeedbackBuilder;
import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.FeedbackNotFoundException;
import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import com.budgetmanagementapp.repository.FeedbackRepository;
import com.budgetmanagementapp.service.FeedbackService;
import com.budgetmanagementapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.mapper.FeedbackMapper.FEEDBACK_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Service
@AllArgsConstructor
@Log4j2
public class FeedbackServiceImpl implements FeedbackService {
    private final UserService userService;
    private final FeedbackRepository feedbackRepo;
    private final FeedbackBuilder feedbackBuilder;

    @Override
    public FeedbackRsModel createFeedback(FeedbackRqModel requestBody, String username) {
        User user = userService.findByUsername(username);
        Feedback feedback = feedbackRepo.save(feedbackBuilder.buildFeedback(requestBody, user));

        var feedbackRsModel = FEEDBACK_MAPPER_INSTANCE.buildFeedbackResponseModel(feedback);

        log.info(FEEDBACK_CREATED_MSG, user.getUsername(), feedbackRsModel);
        return feedbackRsModel;
    }

    @Override
    public List<FeedbackRsModel> getFeedbacksByUser(String username) {
        User user = userService.findByUsername(username);

        List<FeedbackRsModel> feedbacks = feedbackRepo.allByUser(user)
                .stream()
                .map(FEEDBACK_MAPPER_INSTANCE::buildFeedbackResponseModel)
                .collect(Collectors.toList());

        log.info(ALL_FEEDBACKS_MSG, user.getUsername(), feedbacks);
        return feedbacks;
    }

    @Override
    public FeedbackRsModel getFeedbackById(String feedbackId, String username) {
        FeedbackRsModel feedbackRsModel =
                FEEDBACK_MAPPER_INSTANCE.buildFeedbackResponseModel(feedbackById(feedbackId, username));

        log.info(FEEDBACK_BY_ID_MSG, feedbackId, feedbackRsModel);
        return feedbackRsModel;
    }

    private Feedback feedbackById(String feedbackId, String username) {
        return feedbackRepo.byIdAndUser(feedbackId, userService.findByUsername(username))
                .orElseThrow(() ->
                        new FeedbackNotFoundException(format(UNAUTHORIZED_FEEDBACK_MSG, username, feedbackId)));
    }

}
