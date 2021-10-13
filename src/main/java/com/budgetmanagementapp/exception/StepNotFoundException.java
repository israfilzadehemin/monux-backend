package com.budgetmanagementapp.exception;

public class StepNotFoundException extends AppException{
    public StepNotFoundException(String message) {
        super(message);
        code = 8006;
    }
}
