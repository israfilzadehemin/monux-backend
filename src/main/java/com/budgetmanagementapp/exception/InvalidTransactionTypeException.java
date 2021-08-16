package com.budgetmanagementapp.exception;

public class InvalidTransactionTypeException extends AppException {
    public InvalidTransactionTypeException(String message) {
        super(message);
        code = 3000;
    }
}
