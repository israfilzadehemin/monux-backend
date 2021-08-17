package com.budgetmanagementapp.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    int code;

    public AppException(String message) {
        super(message);
        this.code = getCode();
    }
}
