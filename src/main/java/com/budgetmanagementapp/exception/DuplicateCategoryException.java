package com.budgetmanagementapp.exception;

public class DuplicateCategoryException extends AppException {
    public DuplicateCategoryException(String message) {
        super(message);
        code = 4002;
    }
}
