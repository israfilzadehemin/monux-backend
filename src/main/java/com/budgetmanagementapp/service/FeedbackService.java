package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.feedback.FeedbackRqModel;
import com.budgetmanagementapp.model.feedback.FeedbackRsModel;
import java.util.List;

public interface FeedbackService {
    FeedbackRsModel createFeedback(FeedbackRqModel requestBody, String username);

    List<FeedbackRsModel> getFeedbacksByUser(String username);

    FeedbackRsModel getFeedbackById(String feedbackId, String username);
}

