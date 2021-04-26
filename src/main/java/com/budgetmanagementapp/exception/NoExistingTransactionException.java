package com.budgetmanagementapp.exception;

public class NoExistingTransactionException extends RuntimeException {
    public NoExistingTransactionException(String message) {
        super(message);
    }
}
