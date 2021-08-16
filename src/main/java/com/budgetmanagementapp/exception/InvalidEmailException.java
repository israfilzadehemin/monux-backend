package com.budgetmanagementapp.exception;

public class InvalidEmailException extends AppException {
    public InvalidEmailException(String message) {
        super(message);
        code = 1002;
    }
}
