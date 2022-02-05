package com.budgetmanagementapp.exception;

public class FullNameFormatException extends AppException {
    public FullNameFormatException(String message) {
        super(message);
        code = 1111;
    }
}
