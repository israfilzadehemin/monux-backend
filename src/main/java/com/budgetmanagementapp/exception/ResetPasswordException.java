package com.budgetmanagementapp.exception;
public class ResetPasswordException extends AppException {
    public ResetPasswordException(String message){
        super(message);
        code = 6000;
    }
}
