package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.MsgConstant.ALL_FEEDBACKS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.FEEDBACK_BY_ID_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.FEEDBACK_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_FEEDBACK_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.builder.FeedbackBuilder;
import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.FeedbackNotFoundException;
import com.budgetmanagementapp.mapper.FeedbackMapper;
import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import com.budgetmanagementapp.repository.FeedbackRepository;
import com.budgetmanagementapp.service.FeedbackService;
import com.budgetmanagementapp.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
        Feedback feedback = feedbackBuilder.buildFeedback(requestBody, user);

        log.info(format(FEEDBACK_CREATED_MSG, user.getUsername(), FeedbackMapper.INSTANCE.buildFeedbackResponseModel(feedback)));
        return FeedbackMapper.INSTANCE.buildFeedbackResponseModel(feedback);
    }

    @Override
    public List<FeedbackRsModel> getFeedbacksByUser(String username) {
        User user = userService.findByUsername(username);

        List<FeedbackRsModel> feedbacks = feedbacksByUser(user);

        log.info(format(ALL_FEEDBACKS_MSG, user.getUsername(), feedbacks));
        return feedbacks;
    }

    @Override
    public FeedbackRsModel getFeedbackById(String feedbackId, String username) {
        FeedbackRsModel response = FeedbackMapper.INSTANCE.buildFeedbackResponseModel(feedbackById(feedbackId, username));
        log.info(format(FEEDBACK_BY_ID_MSG, feedbackId, response));
        return response;
    }

    private List<FeedbackRsModel> feedbacksByUser(User user) {
        return feedbackRepo.allByUser(user)
                .stream()
                .map(FeedbackMapper.INSTANCE::buildFeedbackResponseModel)
                .collect(Collectors.toList());
    }

    private Feedback feedbackById(String feedbackId, String username) {
        return feedbackRepo.byIdAndUser(feedbackId, userService.findByUsername(username))
                .orElseThrow(() ->
                        new FeedbackNotFoundException(format(UNAUTHORIZED_FEEDBACK_MSG, username, feedbackId)));
    }

}
