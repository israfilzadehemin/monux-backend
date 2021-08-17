package com.budgetmanagementapp.exception;

public class GenericException extends AppException {
    public GenericException(String message) {
        super(message);
        code = 5002;
    }
}
