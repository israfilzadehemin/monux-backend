package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.FeedbackRqModel;
import com.budgetmanagementapp.model.FeedbackRsModel;
import java.util.List;

public interface FeedbackService {
    FeedbackRsModel createFeedback(FeedbackRqModel requestBody, String username);

    List<FeedbackRsModel> getFeedbacksByUser(String username);

    FeedbackRsModel getFeedbackById(String feedbackId, String username);
}

