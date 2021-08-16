package com.budgetmanagementapp.exception;

public class PasswordMismatchException extends AppException {
    public PasswordMismatchException(String message) {
        super(message);
        code = 1005;
    }
}
