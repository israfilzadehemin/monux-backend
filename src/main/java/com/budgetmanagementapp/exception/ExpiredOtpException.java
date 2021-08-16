package com.budgetmanagementapp.exception;

public class ExpiredOtpException extends AppException {
    public ExpiredOtpException(String message) {
        super(message);
        code = 1000;
    }
}
