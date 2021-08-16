package com.budgetmanagementapp.exception;

public class CategoryTypeNotFoundException extends AppException {
    public CategoryTypeNotFoundException(String message) {
        super(message);
        code = 4001;
    }
}
