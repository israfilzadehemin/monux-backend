package com.budgetmanagementapp.utility;

public class UrlConstant {
    public static final String USER_LOGIN_URL = "/v1/user/login";
    public static final String USER_SIGNUP_URL = "/v1/user/signup";
    public static final String USER_OTP_CONFIRM_URL = "/v1/user/confirm-otp";
    public static final String USER_INFO_URL = "/v1/user/user-info";
    public static final String USER_CREATE_PASSWORD_URL = "/v1/user/create-password";
    public static final String USER_RESET_PASSWORD_URL = "/v1/user/reset-password";
    public static final String USER_FULL_RESET_PASSWORD_URL = "http://localhost:8090/v1/user/reset-password?username=";
    public static final String USER_FORGET_PASSWORD_URL = "/v1/user/forget-password";
    public static final String USER_CREATE_INITIAL_ACCOUNT_URL = "/v1/user/create-initial-account";
    public static final String ACCOUNT_CREATE_URL = "/v1/account/create";
    public static final String ACCOUNT_UPDATE_URL = "/v1/account/update";
    public static final String ACCOUNT_UPDATE_BALANCE_URL = "/v1/account/update-balance";
    public static final String ACCOUNT_GET_ALL_ACCOUNTS_URL = "/v1/account/get-all-accounts";
    public static final String ACCOUNT_GET_ALL_ACCOUNT_TYPES_URL = "/v1/account/get-all-account-types";
    public static final String ACCOUNT_GET_ALL_CURRENCIES_URL = "/v1/account/get-all-currencies";
    public static final String ACCOUNT_TOGGLE_ALLOW_NEGATIVE_URL = "/v1/account/toggle-allow-negative";
    public static final String ACCOUNT_TOGGLE_SHOW_IN_SUM_URL = "/v1/account/toggle-show-in-sum";
    public static final String CATEGORY_CREATE_URL = "/v1/category/create-category";
    public static final String CATEGORY_UPDATE_URL = "/v1/category/update-category";
    public static final String CATEGORY_GET_ALL_CATEGORIES_URL = "/v1/category/get-all-categories";
    public static final String CATEGORY_GET_CATEGORIES_URL = "/v1/category/get-categories";
    public static final String LABEL_CREATE_URL = "/v1/label/create-label";
    public static final String LABEL_UPDATE_URL = "/v1/label/update-label";
    public static final String LABEL_GET_ALL_LABELS_URL = "/v1/label/get-all-labels";
    public static final String LABEL_GET_LABELS_URL = "/v1/label/get-labels";
    public static final String LABEL_TOGGLE_VISIBILITY_URL = "/v1/label/toggle-visibility";
    public static final String FEEDBACK_CREATE_URL = "/v1/feedback/create";
    public static final String FEEDBACK_GET_ALL_FEEDBACKS_URL = "/v1/feedback/get-all-feedbacks";
    public static final String FEEDBACK_GET_FEEDBACK_BY_ID_URL = "/v1/feedback/get-by-id";
    public static final String TRANSACTION_CREATE_INCOME_URL = "/v1/transaction/create-income-transaction";
    public static final String TRANSACTION_CREATE_OUTGOING_URL = "/v1/transaction/create-outgoing-transaction";
    public static final String TRANSACTION_CREATE_TRANSFER_URL = "/v1/transaction/create-transfer-transaction";
    public static final String TRANSACTION_CREATE_DEBT_IN_URL = "/v1/transaction/create-debt-in-transaction";
    public static final String TRANSACTION_CREATE_DEBT_OUT_URL = "/v1/transaction/create-debt-out-transaction";
    public static final String TRANSACTION_UPDATE_IN_OUT_URL = "/v1/transaction/update-in-out-transaction";
    public static final String TRANSACTION_UPDATE_TRANSFER_URL = "/v1/transaction/update-transfer-transaction";
    public static final String TRANSACTION_UPDATE_DEBT_URL = "/v1/transaction/update-debt-transaction";
    public static final String TRANSACTION_GET_ALL_TRANSACTIONS_URL = "/v1/transaction/get-all-transactions";
    public static final String TRANSACTION_GET_LAST_TRANSACTIONS_URL = "/v1/transaction/get-last-transactions";
    public static final String TRANSACTION_GET_LAST_TRANSACTIONS_BY_MONTHS_URL = "/v1/transaction/get-last-transactions-by-months";
    public static final String TRANSACTION_GET_LAST_TRANSACTIONS_BY_WEEKS_URL = "/v1/transaction/get-last-transactions-by-weeks";
    public static final String TRANSACTION_TRANSACTIONS_BETWEEN_TIME_URL = "/v1/transaction/transactions-between-time";
    public static final String TRANSACTION_DELETE_TRANSACTIONS_URL = "/v1/transaction/delete-transactions";
    public static final String TEMPLATE_CREATE_INCOME_URL = "/v1/template/create-income-template";
    public static final String TEMPLATE_CREATE_OUTGOING_URL = "/v1/template/create-outgoing-template";
    public static final String TEMPLATE_CREATE_TRANSFER_URL = "/v1/template/create-transfer-template";
    public static final String TEMPLATE_CREATE_DEBT_IN_URL = "/v1/template/create-debt-in-template";
    public static final String TEMPLATE_CREATE_DEBT_OUT_URL = "/v1/template/create-debt-out-template";
    public static final String TEMPLATE_UPDATE_IN_OUT_URL = "/v1/template/update-in-out-template";
    public static final String TEMPLATE_UPDATE_TRANSFER_URL = "/v1/template/update-transfer-template";
    public static final String TEMPLATE_UPDATE_DEBT_URL = "/v1/template/update-debt-template";
    public static final String TEMPLATE_GET_ALL_TEMPLATES_URL = "/v1/template/get-all-templates";
    public static final String TEMPLATE_DELETE_TEMPLATES_URL = "/v1/template/delete-templates";
    public static final String BLOG_GET_ALL_BLOGS_URL = "/v1/blog/get-all-blogs";
    public static final String BLOG_GET_BLOG_BY_ID_URL = "/v1/blog/get-blog-by-id";
    public static final String BLOG_CREATE_BLOG_URL = "/v1/blog/create-blog";
    public static final String BLOG_UPDATE_BLOG_URL = "/v1/blog/update-blog";
    public static final String BLOG_DELETE_BLOG_URL = "/v1/blog/delete-blog";
    public static final String PLAN_GET_ALL_PLANS_URL = "/v1/plan/get-all-plans";
    public static final String PLAN_ADD_PLAN_URL = "/v1/plan/add-plan";
    public static final String PLAN_UPDATE_PLAN_URL = "/v1/plan/update-plan";
    public static final String PLAN_DELETE_PLAN_URL = "/v1/plan/delete-plan";
    public static final String FEATURE_GET_ALL_FEATURES = "/v1/feature/get-all-features";
    public static final String FEATURE_ADD_FEATURE = "/v1/feature/add-feature";
    public static final String FEATURE_UPDATE_FEATURE = "/v1/feature/update-feature";
    public static final String FEATURE_DELETE_FEATURE = "/v1/feature/delete-feature";
    public static final String DEFINITION_GET_ALL_DEFINITIONS_URL = "/v1/definition/get-all-definitions";
    public static final String DEFINITION_CREATE_URL = "/v1/definition/create-definition";
    public static final String DEFINITION_UPDATE_URL = "/v1/definition/update-definition";
    public static final String DEFINITION_DELETE_URL = "/v1/definition/delete-definition";
    public static final String STEP_GET_ALL_STEPS_URL = "/v1/step/get-all-steps";
    public static final String SERVICE_GET_ALL_SERVICES_URL = "/v1/service/get-all-services";
    public static final String BANNER_GET_BANNER_BY_ID_URL = "/v1/banner/get-banner-by-id";
    public static final String BANNER_GET_BANNER_BY_KEYWORD_URL = "/v1/banner/get-banner-by-keyword";
    public static final String FAQ_GET_ALL_FAQS_URL = "/v1/faq/get-all-faqs";
    public static final String FAQ_GET_FAQ_BY_ID_URL = "/v1/faq/get-faq-by-id";
    public static final String FAQ_CREATE_URL = "/v1/faq/create-faq";
    public static final String FAQ_UPDATE_URL = "/v1/faq/update-faq";
    public static final String FAQ_DELETE_URL = "/v1/faq/delete-faq";
    public static final String STEP_CREATE_URL = "/v1/step/create-step";
    public static final String STEP_UPDATE_URL = "/v1/step/update-step";
    public static final String STEP_DELETE_URL = "/v1/step/delete-step";
    public static final String SERVICE_CREATE_URL = "/v1/service/create-service";
    public static final String SERVICE_UPDATE_URL = "/v1/service/update-service";
    public static final String SERVICE_DELETE_URL = "/v1/service/delete-service";
    public static final String BANNER_CREATE_URL = "/v1/banner/create-banner";
    public static final String BANNER_UPDATE_URL = "/v1/banner/update-banner";
    public static final String BANNER_DELETE_URL = "/v1/banner/delete-banner";

}
