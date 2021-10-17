package com.budgetmanagementapp.exception;

public class DefinitionNotFoundException extends AppException{
    public DefinitionNotFoundException(String message) {
        super(message);
        code = 8005;
    }
}
