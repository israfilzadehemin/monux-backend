package com.budgetmanagementapp.exception;

public class FeedbackNotFoundException extends AppException {
    public FeedbackNotFoundException(String message) {
        super(message);
        code = 5001;
    }
}
