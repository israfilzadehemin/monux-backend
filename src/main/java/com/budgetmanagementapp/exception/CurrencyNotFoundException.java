package com.budgetmanagementapp.exception;

public class CurrencyNotFoundException extends AppException {
    public CurrencyNotFoundException(String message) {
        super(message);
        code = 2002;
    }
}
