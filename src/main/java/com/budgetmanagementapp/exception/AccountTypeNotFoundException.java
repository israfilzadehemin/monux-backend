package com.budgetmanagementapp.exception;

public class AccountTypeNotFoundException extends AppException {
    public AccountTypeNotFoundException(String message) {
        super(message);
        code = 2001;
    }
}
