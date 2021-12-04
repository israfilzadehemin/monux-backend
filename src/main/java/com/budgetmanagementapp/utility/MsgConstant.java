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
    public static final String CATEGORY_CREATED_MSG = "User %s created a category: %s";
    public static final String FEEDBACK_CREATED_MSG = "User %s created a feedback: %s";
    public static final String LABEL_CREATED_MSG = "User %s created a label: %s";
    public static final String IN_OUT_TRANSACTION_CREATED_MSG = "User %s created an IN-OUT transaction: %s";
    public static final String IN_OUT_TEMPLATE_CREATED_MSG = "User %s created an IN-OUT template: %s";
    public static final String IN_OUT_TRANSACTION_UPDATED_MSG = "User %s updated an IN-OUT transaction: %s";
    public static final String IN_OUT_TEMPLATE_UPDATED_MSG = "User %s updated an IN-OUT template: %s";
    public static final String DEBT_TRANSACTION_CREATED_MSG = "User %s created a debt transaction: %s";
    public static final String DEBT_TEMPLATE_CREATED_MSG = "User %s created a debt template: %s";
    public static final String DEBT_TRANSACTION_UPDATED_MSG = "User %s updated a debt transaction: %s";
    public static final String DEBT_TEMPLATE_UPDATED_MSG = "User %s updated a debt template: %s";
    public static final String TRANSFER_TRANSACTION_CREATED_MSG = "User %s created a transfer transaction: %s";
    public static final String TRANSFER_TEMPLATE_CREATED_MSG = "User %s created a transfer template: %s";
    public static final String TRANSFER_TRANSACTION_UPDATED_MSG = "User %s updated a transfer transaction: %s";
    public static final String TRANSFER_TEMPLATE_UPDATED_MSG = "User %s updated a transfer template: %s";
    public static final String ACCOUNT_UPDATED_MSG = "User %s updated an account: %s";
    public static final String BALANCE_UPDATED_MSG = "User '%s' updated balance of account %s: %s";
    public static final String CATEGORY_UPDATED_MSG = "User '%s' updated a category: %s";
    public static final String LABEL_UPDATED_MSG = "User '%s' updated a label: %s";
    public static final String LABEL_NOT_FOUND_MSG = "User '%s' does not have any labels";
    public static final String TRANSACTION_NOT_FOUND_MSG = "User '%s' does not have any transactions";
    public static final String CATEGORY_TYPE_NOT_FOUND_MSG = "Category type '%s' not found";
    public static final String TRANSACTION_TYPE_NOT_FOUND_MSG = "Transaction type '%s' not found";
    public static final String CURRENCY_NOT_FOUND_MSG = "Currency '%s' is not found";
    public static final String ROLE_NOT_FOUND_MSG = "Role is not found in database";
    public static final String PASSWORD_CREATED_MSG = "Password has been created for %s";
    public static final String PASSWORD_UPDATED_MSG = "Password has been updated for %s";
    public static final String PASSWORD_EQUALITY_MSG = "Password and confirm password is not same";
    public static final String REQUEST_MSG = "%s request with body: %s";
    public static final String REQUEST_PARAM_MSG = "%s request with param: %s";
    public static final String OTP_CONFIRMED_MSG = "User %s confirmed their OTP";
    public static final String NO_BODY_MSG = "No body required";
    public static final String DUPLICATE_ACCOUNT_NAME_MSG = "User %s has existing account %s";
    public static final String DUPLICATE_CATEGORY_NAME_MSG = "User '%s' has existing category '%s'";
    public static final String DUPLICATE_LABEL_NAME_MSG = "User '%s' has existing label '%s'";
    public static final String UNAUTHORIZED_ACCOUNT_MSG = "User '%s' does not own account '%s'";
    public static final String UNAUTHORIZED_CATEGORY_MSG = "User '%s' does not own category '%s'";
    public static final String UNAUTHORIZED_LABEL_MSG = "User '%s' does not own label '%s'";
    public static final String UNAUTHORIZED_FEEDBACK_MSG = "User '%s' does not own feedback '%s'";
    public static final String UNAUTHORIZED_TRANSACTION_MSG = "User '%s' does not own any of transactions: '%s'";
    public static final String UNAUTHORIZED_TEMPLATE_MSG = "User '%s' does not own template '%s'";
    public static final String ALLOW_NEGATIVE_TOGGLED_MSG = "User '%s' changed 'Allow negative' property for: %s";
    public static final String SHOW_IN_SUM_TOGGLED_MSG = "User '%s' changed 'Show in sum' property for: %s";
    public static final String VISIBILITY_TOGGLED_MSG = "User '%s' changed 'Visibility' property for: %s";
    public static final String ALL_ACCOUNTS_MSG = "All accounts of %s: %s";
    public static final String ALL_ACCOUNT_TYPES_MSG = "All account types: %s";
    public static final String ALL_CURRENCIES_MSG = "All currencies: %s";
    public static final String ALL_CATEGORIES_MSG = "All categories of %s: %s";
    public static final String ALL_TRANSACTIONS_MSG = "All transactions of %s: %s";
    public static final String LAST_TRANSACTIONS_MSG = "Last transactions of %s: %s";
    public static final String LAST_TRANSACTIONS_BY_MONTHS_MSG = "Last transactions during 12 months of %s: %s";
    public static final String LAST_TRANSACTIONS_BY_WEEKS_MSG = "Last transactions during 12 weeks of %s: %s";
    public static final String TRANSACTIONS_BETWEEN_TIME_MSG = "Transactions between time of %s: %s";
    public static final String DELETED_TRANSACTIONS_MSG = "Deleted transactions of %s: %s";
    public static final String ALL_TEMPLATES_MSG = "All templates of %s: %s";
    public static final String DELETED_TEMPLATES_MSG = "Deleted templates of %s: %s";
    public static final String ALL_LABELS_MSG = "All labels of %s: %s";
    public static final String ALL_FEEDBACKS_MSG = "All feedbacks of %s: %s";
    public static final String FEEDBACK_BY_ID_MSG = "Feedback with ID '%s': %s";
    public static final String INVALID_CATEGORY_ID_MSG = "Category '%s' is not found";
    public static final String INVALID_TRANSACTION_TYPE_MSG = "Transaction type '%s' is not valid";
    public static final String TRANSFER_TO_SELF_MSG = "Sender and receiver accounts are the same";
    public static final String INSUFFICIENT_BALANCE_MSG = "There is no enough balance for this operation in '%s'";
    public static final String NEGATIVE_BALANCE_NOT_ALLOWED = "Negative balance is not allowed for '%s'";
    public static final String INVALID_DATE_TIME_FORMAT_MSG = "DateTime should be in following format: 'yyyy-MM-dd HH:mm'";
    public static final String INVALID_RESET_PASSWORD_MSG = "Reset password exception";
    public static final String BLOG_CREATED_MSG = "Blog created: %s";
    public static final String BLOG_UPDATED_MSG = "Blog updated: %s";
    public static final String BLOG_DELETED_MSG = "Blog deleted: %s";
    public static final String BLOG_NOT_FOUND_MSG = "Blog is not found: '%s'";
    public static final String BLOG_WITH_PARAM = "%s blog with param: %s";
    public static final String PLAN_NOT_FOUND_MSG = "Plan is not found: '%s'";
    public static final String PLAN_CREATED_MSG = "Plan created: %s";
    public static final String PLAN_UPDATED_MSG = "Plan updated: %s";
    public static final String PLAN_DELETED_MSG = "Plan deleted: %s";
    public static final String FEATURE_CREATED_MSG = "Feature created: %s";
    public static final String FEATURE_UPDATED_MSG = "Feature updated: %s";
    public static final String FEATURE_DELETED_MSG = "Feature deleted: %s";
    public static final String FEATURE_NOT_FOUND_MSG = "Feature not found: %s";
    public static final String FAQ_NOT_FOUND_MSG = "Faq not found: '%s'";
    public static final String FAQ_CREATED_MSG = "Faq created: %s";
    public static final String FAQ_UPDATED_MSG = "Faq updated: %s";
    public static final String FAQ_DELETED_MSG = "Faq deleted: %s";
    public static final String DEFINITION_CREATED_MSG = "Definition created: %s";
    public static final String DEFINITION_UPDATED_MSG = "Definition updated: %s";
    public static final String DEFINITION_DELETED_MSG = "Definition deleted: %s";
    public static final String DEFINITION_NOT_FOUND_MSG = "Definition not found: '%s'";
    public static final String STEP_NOT_FOUND_MSG = "Step not found: '%s'";
    public static final String STEP_CREATED_MSG = "Step created: %s";
    public static final String STEP_UPDATED_MSG = "Step updated: %s";
    public static final String STEP_DELETED_MSG = "Step deleted: %s";
    public static final String SERVICE_NOT_FOUND_MSG = "Service not found: '%s'";
    public static final String SERVICE_CREATED_MSG = "Service created: %s";
    public static final String SERVICE_UPDATED_MSG = "Service updated: %s";
    public static final String SERVICE_DELETED_MSG = "Service deleted: %s";
    public static final String BANNER_NOT_FOUND_MSG = "Banner not found: '%s'";
    public static final String BANNER_CREATED_MSG = "Banner created: %s";
    public static final String BANNER_UPDATED_MSG = "Banner updated: %s";
    public static final String BANNER_DELETED_MSG = "Banner deleted: %s";
    public static final String RATE_VALUE_EXCEPTION = "Rate can not be equals to or less than zero";

}
