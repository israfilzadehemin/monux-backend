package com.budgetmanagementapp.exception;

public class DuplicateAccountException extends AppException {
    public DuplicateAccountException(String message) {
        super(message);
        code = 2003;
    }
}
