package com.budgetmanagementapp.exception;

public class UsernameNotUniqueException extends AppException {
    public UsernameNotUniqueException(String message) {
        super(message);
        code = 1007;
    }
}
