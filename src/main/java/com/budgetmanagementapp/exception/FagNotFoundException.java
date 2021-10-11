package com.budgetmanagementapp.exception;

public class FagNotFoundException extends AppException{
    public FagNotFoundException(String message) {
        super(message);
        code = 8004;
    }
}
