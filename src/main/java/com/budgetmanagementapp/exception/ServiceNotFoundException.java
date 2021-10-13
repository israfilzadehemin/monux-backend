package com.budgetmanagementapp.exception;

public class ServiceNotFoundException extends AppException{
    public ServiceNotFoundException(String message) {
        super(message);
        code = 8007;
    }
}
