package com.budgetmanagementapp.utility;

public class Constant {
    public static final String ACCOUNT_ALL = "all";
    public static final String JWT_PREFIX = "Bearer ";
    public static final String JWT_HEADER = "token";
    public static final String JWT_TOKEN_FORMAT = "%s%s";
    public static final String HEADER_REMEMBER_ME = "rememberMe";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String SORT_BY_DATETIME = "dateTime";
    public static final String SORT_DIR_DESC = "desc";
    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_OPEN = "OPEN";
    public static final String STATUS_USED = "USED";
    public static final String CASH_ACCOUNT = "cashAccount";
    public static final String CARD_ACCOUNT = "cardAccount";
    public static final String INVESTMENT_ACCOUNT = "investmentAccount";
    public static final String SAVING_ACCOUNT = "savingAccount";
    public static final String OTP_CONFIRMATION_SUBJECT = "Confirmation code for your account";
    public static final String OTP_CONFIRMATION_BODY = "Your confirmation code is: %s";
    public static final String RESET_PASSWORD_SUBJECT = "OTP for resetting your Monux password";
    public static final String RESET_PASSWORD_BODY = "Your reset password OTP is: %s";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String SENDER_ACCOUNT = "senderAccount";
    public static final String RECEIVER_ACCOUNT = "receiverAccount";
    public static final String COMMON_USERNAME = "commonUser";

    public static final String ENCRYPT_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String SECRET_KEY = "AES";
}
