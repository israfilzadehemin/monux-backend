package com.budgetmanagementapp.exception;

public class InsufficientBalanceException extends AppException {
    public InsufficientBalanceException(String message) {
        super(message);
        code = 2004;
    }
}
