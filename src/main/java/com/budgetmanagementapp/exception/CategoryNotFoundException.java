package com.budgetmanagementapp.exception;

public class CategoryNotFoundException extends AppException {
    public CategoryNotFoundException(String message) {
        super(message);
        code = 4000;
    }
}
