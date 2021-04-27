package com.budgetmanagementapp.utility;

public class UrlConstant {
    public static final String H2_CONSOLE_URL = "/h2-console/**";
    public static final String USER_LOGIN_URL = "/v1/user/login";
    public static final String USER_SIGNUP_WITH_EMAIL_URL = "/v1/user/signup-with-email";
    public static final String USER_SIGNUP_WITH_PHONE_NUMBER_URL = "/v1/user/signup-with-phone-number";
    public static final String USER_OTP_CONFIRM_URL = "/v1/user/confirm-otp";
    public static final String USER_CREATE_PASSWORD_URL = "/v1/user/create-password";
    public static final String USER_CREATE_INITIAL_ACCOUNT_URL = "/v1/user/create-initial-account";
    public static final String ACCOUNT_CREATE_URL = "/v1/account/create";
    public static final String ACCOUNT_UPDATE_URL = "/v1/account/update";
    public static final String ACCOUNT_UPDATE_BALANCE_URL = "/v1/account/update-balance";
    public static final String ACCOUNT_GET_ALL_ACCOUNTS_URL = "/v1/account/get-all-accounts";
    public static final String ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL = "/v1/account/toggle-allow-negative";
    public static final String ACCOUNT_TOGGLE_SHOW_IN_SUM_URL = "/v1/account/toggle-show-in-sum";
    public static final String CATEGORY_CREATE_CATEGORY_URL = "/v1/category/create-category";
    public static final String CATEGORY_UPDATE_CATEGORY_URL = "/v1/category/update-category";
    public static final String CATEGORY_GET_ALL_CATEGORIES_URL = "/v1/category/get-all-categories";
    public static final String TRANSACTION_GET_ALL_TRANSACTIONS_URL = "/v1/transaction/get-all-transactions";
    public static final String CATEGORY_GET_CATEGORIES_URL = "/v1/category/get-categories";
    public static final String TAG_CREATE_TAG_URL = "/v1/tag/create-tag";
    public static final String TAG_UPDATE_TAG_URL = "/v1/tag/update-tag";
    public static final String TAG_GET_ALL_TAGS_URL = "/v1/tag/get-all-tags";
    public static final String TAG_GET_TAGS_URL = "/v1/tag/get-tags";
    public static final String TAG_TOGGLE_VISIBILITY_URL = "/v1/tag/toggle-visibility";
    public static final String FEEDBACK_CREATE_URL = "/v1/feedback/create";
    public static final String FEEDBACK_GET_ALL_FEEDBACKS_URL = "/v1/feedback/get-all-feedbacks";
    public static final String FEEDBACK_GET_FEEDBACK_BY_ID_URL = "/v1/feedback/get-by-id";
    public static final String TRANSACTION_CREATE_INCOME_TRANSACTION_URL = "/v1/transaction/create-income-transaction";
    public static final String TRANSACTION_UPDATE_IN_OUT_TRANSACTION_URL = "/v1/transaction/update-in-out-transaction";
    public static final String TRANSACTION_UPDATE_TRANSFER_TRANSACTION_URL =
            "/v1/transaction/update-transfer-transaction";
    public static final String TRANSACTION_UPDATE_DEBT_TRANSACTION_URL =
            "/v1/transaction/update-debt-transaction";
    public static final String TRANSACTION_CREATE_DEBT_IN_TRANSACTION_URL =
            "/v1/transaction/create-debt-in-transaction";
    public static final String TRANSACTION_CREATE_DEBT_OUT_TRANSACTION_URL =
            "/v1/transaction/create-debt-out-transaction";
    public static final String TRANSACTION_CREATE_TRANSFER_TRANSACTION_URL =
            "/v1/transaction/create-transfer-transaction";
    public static final String TRANSACTION_CREATE_OUTCOME_TRANSACTION_URL =
            "/v1/transaction/create-outcome-transaction";
}
