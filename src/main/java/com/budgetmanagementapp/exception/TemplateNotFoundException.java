package com.budgetmanagementapp.exception;

public class TemplateNotFoundException extends AppException {
    public TemplateNotFoundException(String message) {
        super(message);
        code = 5005;
    }
}
