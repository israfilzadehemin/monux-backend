package com.budgetmanagementapp.exception;

public class BannerNotFoundException extends AppException{
    public BannerNotFoundException(String message) {
        super(message);
        code = 8003;
    }
}
