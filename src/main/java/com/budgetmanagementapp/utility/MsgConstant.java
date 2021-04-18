package com.budgetmanagementapp.utility;

public class MsgConstant {
    public static final String JWT_TOKEN_GENERATED_MSG = "JWT token generated for %s";
    public static final String INVALID_REQUEST_MODEL_MSG = "Request body is invalid or missed";
    public static final String INVALID_REQUEST_PARAM_MSG = "Request param is invalid or missed";
    public static final String INVALID_INITIAL_ACCOUNT_MODEL_MSG =
            "'username', 'accountName', 'currency' and 'balance' fields are mandatory for initial account";
    public static final String INVALID_EMAIL_MSG = "Email '%s' does not follow valid email format rules";
    public static final String INVALID_PHONE_NUMBER_MSG =
            "Phone number '%s' does not follow valid phone number format rules";
    public static final String INVALID_CREDENTIALS_MSG = "Invalid credentials";
    public static final String INVALID_OTP_MSG = "Invalid OTP";
    public static final String INITIAL_ACCOUNT_EXISTING_MSG = "User: %s has existing initial account";
    public static final String EXPIRED_OTP_MSG = "OTP Expired";
    public static final String UNEXPECTED_EXCEPTION_MSG = "Unexpected exception thrown";
    public static final String USER_NOT_FOUND_MSG = "User %s is not found in the database";
    public static final String USER_ADDED_MSG = "User %s successfully added to database";
    public static final String USERNAME_NOT_UNIQUE_MSG = "This email or number has already been registered";
    public static final String USER_BY_OTP_NOT_FOUND_MSG = "User is not found by OTP: %s";
    public static final String ACCOUNT_TYPE_NOT_FOUND_MSG = "Account type  %s is not found";
    public static final String ACCOUNT_CREATED_MSG = "User %s created an account: %s";
    public static final String CUSTOM_CATEGORY_CREATED_MSG = "User %s created a custom category: %s";
    public static final String CUSTOM_TAG_CREATED_MSG = "User %s created a custom tag: %s";
    public static final String ACCOUNT_UPDATED_MSG = "User %s updated an account: %s";
    public static final String BALANCE_UPDATED_MSG = "User %s updated balance of account %s: %s";
    public static final String CATEGORY_UPDATED_MSG = "User %s updated a category: %s";
    public static final String TAG_UPDATED_MSG = "User %s updated a tag: %s";
    public static final String ACCOUNT_NOT_FOUND_MSG = "User %s does not have existing account";
    public static final String CUSTOM_CATEGORY_NOT_FOUND_MSG = "User %s does not have any custom categories";
    public static final String CUSTOM_TAG_NOT_FOUND_MSG = "User %s does not have any custom tags";
    public static final String CATEGORY_TYPE_NOT_FOUND_MSG = "Category type '%s' not found";
    public static final String CURRENCY_NOT_FOUND_MSG = "Currency '%s' is not found";
    public static final String ROLE_NOT_FOUND_MSG = "Role is not found in database";
    public static final String PASSWORD_CREATED_MSG = "Password has been created for %s";
    public static final String PASSWORD_MISMATCH_MSG = "Password and confirm password must be the same";
    public static final String PASSWORD_NOT_SUFFICIENT_MSG = "Password must be at least 5 symbols long";
    public static final String REQUEST_MSG = "%s request with body: %s";
    public static final String OTP_CONFIRMED_MSG = "User %s confirmed their OTP";
    public static final String NO_BODY_MSG = "No body required";
    public static final String DUPLICATE_ACCOUNT_NAME_MSG = "User %s has existing account %s";
    public static final String DUPLICATE_CATEGORY_NAME_MSG = "User '%s' has existing category '%s'";
    public static final String DUPLICATE_TAG_NAME_MSG = "User '%s' has existing category '%s'";
    public static final String UNAUTHORIZED_ACCOUNT_MSG = "User '%s' does not own account '%s'";
    public static final String UNAUTHORIZED_CATEGORY_MSG = "User '%s' does not own category '%s'";
    public static final String UNAUTHORIZED_TAG_MSG = "User '%s' does not own tag '%s'";
    public static final String ALLOW_NEGATIVE_TOGGLED_MSG = "User '%s' changed 'Allow negative' property for: %s";
    public static final String SHOW_IN_SUM_TOGGLED_MSG = "User '%s' changed 'Show in sum' property for: %s";
    public static final String VISIBILITY_TOGGLED_MSG = "User '%s' changed 'Visibility' property for: %s";
    public static final String ALL_ACCOUNTS_MSG = "All accounts of %s: %s";
    public static final String ALL_CATEGORIES_MSG = "All categories of %s: %s";
    public static final String ALL_TAGS_MSG = "All tags of %s: %s";
}
