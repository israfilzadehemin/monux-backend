package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.FeedbackRequestModel;
import com.budgetmanagementapp.model.FeedbackResponseModel;
import java.util.List;

public interface FeedbackService {
    FeedbackResponseModel createFeedback(FeedbackRequestModel requestBody, String username);

    List<FeedbackResponseModel> getFeedbacksByUser(String username);

    FeedbackResponseModel getFeedbackById(String feedbackId, String username);
}

