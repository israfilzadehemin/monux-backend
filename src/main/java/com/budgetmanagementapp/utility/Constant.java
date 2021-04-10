package com.budgetmanagementapp.utility;

public class Constant {
    public static final String JWT_PREFIX = "Bearer ";
    public static final String JWT_HEADER = "token";
    public static final String JWT_TOKEN_FORMAT = "%s%s";
    public static final String JWT_TOKEN_GENERATED_MSG = "JWT token generated for %s";
    public static final String USER_LOGIN_URL = "/v1/user/login";
    public static final String H2_CONSOLE_URL = "/h2-console/**";
    public static final String USER_CONTROLLER_URL = "/v1/user";
    public static final String HEADER_REMEMBER_ME = "rememberMe";
    public static final String INVALID_REQUEST_MODEL_MSG = "Request body is invalid";
    public static final String INVALID_CREDENTIALS_MSG = "Invalid credentials";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String UNEXPECTED_EXCEPTION_MSG = "Unexpected exception thrown";
    public static final String USER_NOT_FOUND_MSG = "User %s is not found in the database";
}
