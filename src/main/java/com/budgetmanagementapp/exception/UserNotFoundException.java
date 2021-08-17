package com.budgetmanagementapp.exception;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(String message) {
        super(message);
        code = 1008;
    }
}
