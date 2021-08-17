package com.budgetmanagementapp.exception;

public class UserRoleNotFoundException extends AppException {
    public UserRoleNotFoundException(String message) {
        super(message);
        code = 1009;
    }
}
