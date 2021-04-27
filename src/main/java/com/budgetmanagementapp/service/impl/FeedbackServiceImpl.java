package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.STATUS_ACTIVE;
import static com.budgetmanagementapp.utility.Constant.STATUS_OPEN;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_FEEDBACKS_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.FEEDBACK_BY_ID_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.FEEDBACK_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.FEEDBACK_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.UNAUTHORIZED_FEEDBACK_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_NOT_FOUND_MSG;

import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.FeedbackNotFoundException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.FeedbackRequestModel;
import com.budgetmanagementapp.model.FeedbackResponseModel;
import com.budgetmanagementapp.repository.FeedbackRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.FeedbackService;
import com.budgetmanagementapp.utility.CustomValidator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class FeedbackServiceImpl implements FeedbackService {
    private final UserRepository userRepo;
    private final FeedbackRepository feedbackRepo;

    @Override
    public FeedbackResponseModel createFeedback(FeedbackRequestModel requestBody, String username) {
        CustomValidator.validateFeedbackModel(requestBody);

        User user = findUserByUsername(username);

        Feedback feedback = buildFeedback(requestBody, user);

        log.info(String.format(FEEDBACK_CREATED_MSG, user.getUsername(), buildFeedbackResponseModel(feedback)));
        return buildFeedbackResponseModel(feedback);
    }

    @Override
    public List<FeedbackResponseModel> getFeedbacksByUser(String username) {
        User user = findUserByUsername(username);

        List<FeedbackResponseModel> feedbacks = feedbackRepo.allByUser(user)
                .stream()
                .map(this::buildFeedbackResponseModel)
                .collect(Collectors.toList());

        if (feedbacks.isEmpty()) {
            throw new FeedbackNotFoundException(String.format(FEEDBACK_NOT_FOUND_MSG, username));
        }

        log.info(String.format(ALL_FEEDBACKS_MSG, user.getUsername(), feedbacks));
        return feedbacks;
    }

    @Override
    public FeedbackResponseModel getFeedbackById(String feedbackId, String username) {
        CustomValidator.validateFeedbackId(feedbackId);

        FeedbackResponseModel responseModel =
                buildFeedbackResponseModel(feedbackRepo.byIdAndUser(feedbackId, findUserByUsername(username))
                        .orElseThrow(() ->
                                new FeedbackNotFoundException(
                                        String.format(UNAUTHORIZED_FEEDBACK_MSG, username, feedbackId))));

        log.info(String.format(FEEDBACK_BY_ID_MSG, feedbackId, responseModel));
        return responseModel;
    }

    private User findUserByUsername(String username) {
        return userRepo
                .findByUsernameAndStatus(username, STATUS_ACTIVE)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    private Feedback buildFeedback(FeedbackRequestModel requestBody, User user) {
        return feedbackRepo.save(Feedback.builder()
                .feedbackId(UUID.randomUUID().toString())
                .description(requestBody.getDescription())
                .creationDateTime(LocalDateTime.now())
                .status(STATUS_OPEN)
                .user(user)
                .build());
    }

    private FeedbackResponseModel buildFeedbackResponseModel(Feedback feedback) {
        return FeedbackResponseModel.builder()
                .feedbackId(feedback.getFeedbackId())
                .description(feedback.getDescription())
                .creationDateTime(feedback.getDateTime())
                .status(feedback.getStatus())
                .build();
    }

}
