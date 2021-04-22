package com.budgetmanagementapp.utility;

import static com.budgetmanagementapp.utility.MsgConstant.CATEGORY_TYPE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_EMAIL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_INITIAL_ACCOUNT_MODEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_PHONE_NUMBER_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_REQUEST_MODEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_REQUEST_PARAM_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_MISMATCH_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_NOT_SUFFICIENT_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSACTION_TYPE_NOT_FOUND_MSG;

import com.budgetmanagementapp.exception.CategoryTypeNotFoundException;
import com.budgetmanagementapp.exception.InvalidEmailException;
import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.exception.InvalidPhoneNumberException;
import com.budgetmanagementapp.exception.PasswordMismatchException;
import com.budgetmanagementapp.exception.PasswordNotSufficientException;
import com.budgetmanagementapp.exception.TransactionTypeNotFoundException;
import com.budgetmanagementapp.model.AccountRequestModel;
import com.budgetmanagementapp.model.CategoryRequestModel;
import com.budgetmanagementapp.model.FeedbackRequestModel;
import com.budgetmanagementapp.model.InOutRequestModel;
import com.budgetmanagementapp.model.TagRequestModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
import com.budgetmanagementapp.model.UpdateCategoryModel;
import com.budgetmanagementapp.model.UpdateTagModel;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class CustomValidator {

    public static void validateEmailFormat(String email) {
        if (email == null) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }

        if (!email.matches(
                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) {
            throw new InvalidEmailException(String.format(INVALID_EMAIL_MSG, email));
        }

    }

    public static void validatePhoneNumberFormat(String phoneNumber) {
        if (phoneNumber == null) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }

        if (!phoneNumber.startsWith("+") || phoneNumber.length() < 10) {
            throw new InvalidPhoneNumberException(String.format(INVALID_PHONE_NUMBER_MSG, phoneNumber));
        }

        try {
            Long.parseLong(phoneNumber.substring(1));
        } catch (Exception exception) {
            throw new InvalidPhoneNumberException(String.format(INVALID_PHONE_NUMBER_MSG, phoneNumber));
        }
    }

    public static void validatePassword(String password, String confirmPassword) {

        if (password == null || confirmPassword == null) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }

        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException(PASSWORD_MISMATCH_MSG);
        }

        if (password.length() < 5) {
            throw new PasswordNotSufficientException(PASSWORD_NOT_SUFFICIENT_MSG);
        }
    }

    public static void validateAccountModel(AccountRequestModel requestModel, boolean isInitialAccount) {

        if (isInitialAccount) {
            if (requestModel.getUsername() == null || requestModel.getUsername().isBlank()
                    || requestModel.getAccountName() == null || requestModel.getAccountName().isBlank()
                    || requestModel.getCurrency() == null || requestModel.getCurrency().isBlank()
                    || requestModel.getBalance() == null
            ) {
                throw new InvalidModelException(INVALID_INITIAL_ACCOUNT_MODEL_MSG);
            }
        } else {
            if (requestModel.getIcon() == null || requestModel.getIcon().isBlank()
                    || requestModel.getAccountName() == null || requestModel.getAccountName().isBlank()
                    || requestModel.getAccountTypeName() == null
                    || requestModel.getAccountTypeName().isBlank() || requestModel.getCurrency() == null
                    || requestModel.getCurrency().isBlank() || requestModel.getBalance() == null
                    || requestModel.getAllowNegative() == null || requestModel.getShowInSum() == null
            ) {
                throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
            }
        }

    }

    public static void validateUpdateAccountModel(UpdateAccountModel requestModel) {
        if (requestModel.getAccountId() == null || requestModel.getAccountId().isBlank()
                || requestModel.getNewAccountName() == null || requestModel.getNewAccountName().isBlank()
                || requestModel.getIcon() == null || requestModel.getIcon().isBlank()
                || requestModel.getAccountTypeName() == null || requestModel.getAccountTypeName().isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateAccountId(String accountId) {
        if (accountId == null || accountId.isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_PARAM_MSG);
        }
    }

    public static void validateUpdateBalanceModel(UpdateBalanceModel requestModel) {
        if (requestModel.getAccountId() == null || requestModel.getAccountId().isBlank()
                || requestModel.getBalance() == null) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateCategoryType(String value) {
        if ((int) Arrays.stream(CategoryType.values()).filter(type -> type.name().equals(value.toUpperCase()))
                .count() == 0) {
            throw new CategoryTypeNotFoundException(String.format(CATEGORY_TYPE_NOT_FOUND_MSG, value));
        }
    }

    public static void validateCategoryRequestModel(CategoryRequestModel requestModel) {
        if (requestModel.getIcon() == null || requestModel.getIcon().isBlank()
                || requestModel.getCategoryName() == null || requestModel.getCategoryName().isBlank()
                || requestModel.getCategoryTypeName() == null || requestModel.getCategoryTypeName().isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateUpdateCategoryModel(UpdateCategoryModel requestModel) {
        if (requestModel.getIcon() == null || requestModel.getIcon().isBlank()
                || requestModel.getNewCategoryName() == null
                || requestModel.getNewCategoryName().isBlank() || requestModel.getCategoryId() == null
                || requestModel.getCategoryId().isBlank() || requestModel.getNewCategoryType() == null
                || requestModel.getNewCategoryType().isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateTagRequestModel(TagRequestModel requestModel) {
        if (requestModel.getTagName() == null || requestModel.getTagName().isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateUpdateTagModel(UpdateTagModel requestBody) {
        if (requestBody.getTagId() == null || requestBody.getTagId().isBlank()
                || requestBody.getNewTagName() == null || requestBody.getNewTagName().isBlank()
        ) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateTagId(String tagId) {
        if (tagId == null || tagId.isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_PARAM_MSG);
        }

    }

    public static void validateFeedbackModel(FeedbackRequestModel requestBody) {
        if (Objects.isNull(requestBody.getDescription()) || requestBody.getDescription().isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateFeedbackId(String feedbackId) {
        if (Objects.isNull(feedbackId) || feedbackId.isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_PARAM_MSG);
        }
    }

    public static void validateIncomeModel(InOutRequestModel requestBody) {
        if (Objects.isNull(requestBody.getAccountId()) || requestBody.getAccountId().isBlank()
                || Objects.isNull(requestBody.getDescription()) || requestBody.getDescription().isBlank()
                || Objects.isNull(requestBody.getAmount()) || Objects.isNull(requestBody.getCreationDateTime())
                || requestBody.getCreationDateTime().isBlank() || Objects.isNull(requestBody.getCategoryId())
                || Objects.isNull(requestBody.getTagIds())
        ) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateTransactionType(String value) {
        if ((int) Arrays.stream(TransactionType.values()).filter(type -> type.name().equals(value.toUpperCase()))
                .count() == 0) {
            throw new TransactionTypeNotFoundException(String.format(TRANSACTION_TYPE_NOT_FOUND_MSG, value));
        }

    }
}

