package com.budgetmanagementapp.exception;

public class AccountNotFoundException extends AppException {
    public AccountNotFoundException(String message) {
        super(message);
        code = 2000;
    }
}
