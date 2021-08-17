package com.budgetmanagementapp.exception;

public class DuplicateLabelException extends AppException {
    public DuplicateLabelException(String message) {
        super(message);
        code = 5000;
    }
}
