package com.budgetmanagementapp.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends AppException {
    int code;

    public DuplicateException(String message, int code) {
        super(message);
        this.code = code;
    }
}
