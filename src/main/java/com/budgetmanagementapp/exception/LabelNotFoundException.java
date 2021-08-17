package com.budgetmanagementapp.exception;

public class LabelNotFoundException extends AppException {
    public LabelNotFoundException(String message) {
        super(message);
        code = 5004;
    }
}
