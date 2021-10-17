package com.budgetmanagementapp.exception;

public class FaqNotFoundException extends AppException{
    public FaqNotFoundException(String message) {
        super(message);
        code = 8004;
    }
}
