package com.budgetmanagementapp.exception;

public class InitialAccountExistingException extends AppException {
    public InitialAccountExistingException(String message) {
        super(message);
        code = 1001;
    }
}
