package com.budgetmanagementapp.exception;

public class TransactionNotFoundException extends AppException {
    public TransactionNotFoundException(String message) {
        super(message);
        code = 3002;
    }
}
