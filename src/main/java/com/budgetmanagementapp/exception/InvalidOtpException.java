package com.budgetmanagementapp.exception;

public class InvalidOtpException extends AppException {
    public InvalidOtpException(String message) {
        super(message);
        code = 1003;
    }
}
