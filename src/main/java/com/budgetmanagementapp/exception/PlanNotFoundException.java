package com.budgetmanagementapp.exception;

public class PlanNotFoundException extends AppException{
    public PlanNotFoundException(String message) {
        super(message);
        code = 8001;
    }
}
