package com.budgetmanagementapp.exception;

public class DuplicatePasswordException extends AppException {
    public DuplicatePasswordException(String message) {
        super(message);
        code = 1105;
    }
}
