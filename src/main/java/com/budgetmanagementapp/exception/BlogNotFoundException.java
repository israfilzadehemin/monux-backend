package com.budgetmanagementapp.exception;

public class BlogNotFoundException extends AppException{
    public BlogNotFoundException(String message) {
        super(message);
        code = 8000;
    }
}
