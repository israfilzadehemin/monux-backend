package com.budgetmanagementapp.exception;

public class InvalidModelException extends AppException {
    public InvalidModelException(String message) {
        super(message);
        code = 5003;
    }
}
