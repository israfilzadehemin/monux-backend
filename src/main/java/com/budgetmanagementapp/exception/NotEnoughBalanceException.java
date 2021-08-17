package com.budgetmanagementapp.exception;

public class NotEnoughBalanceException extends AppException {
    public NotEnoughBalanceException(String message) {
        super(message);
        code = 2004;
    }
}
