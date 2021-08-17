package com.budgetmanagementapp.exception;

public class NoExistingTransactionException extends AppException {
    public NoExistingTransactionException(String message) {
        super(message);
        code = 3001;
    }
}
