package com.budgetmanagementapp.exception;

public class FeatureNotFoundException extends AppException{
    public FeatureNotFoundException(String message) {
        super(message);
        code = 8006;
    }
}
