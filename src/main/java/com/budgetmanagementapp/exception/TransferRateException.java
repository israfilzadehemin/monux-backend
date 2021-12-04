package com.budgetmanagementapp.exception;

public class TransferRateException extends AppException {
    public TransferRateException(String message) {
        super(message);
        code = 8000;
    }
}
