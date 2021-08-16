package com.budgetmanagementapp.exception;

public class TransactionTypeNotFoundException extends AppException {
    public TransactionTypeNotFoundException(String message) {
        super(message);
        code = 3003;
    }
}
