package com.budgetmanagementapp.exception;

public class TransferToSelfException extends AppException {
    public TransferToSelfException(String message) {
        super(message);
        code = 3004;
    }
}
