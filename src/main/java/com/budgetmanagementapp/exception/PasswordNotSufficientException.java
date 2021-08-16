package com.budgetmanagementapp.exception;

public class PasswordNotSufficientException extends AppException {
    public PasswordNotSufficientException(String message) {
        super(message);
        code = 1006;
    }
}
