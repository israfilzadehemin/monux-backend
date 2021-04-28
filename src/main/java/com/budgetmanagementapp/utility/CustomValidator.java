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
import static com.budgetmanagementapp.utility.MsgConstant.TRANSFER_TO_SELF_MSG;
import static com.budgetmanagementapp.utility.TransactionType.INCOME;
import static com.budgetmanagementapp.utility.TransactionType.OUTGOING;
import static com.budgetmanagementapp.utility.TransactionType.values;

import com.budgetmanagementapp.exception.CategoryTypeNotFoundException;
import com.budgetmanagementapp.exception.DuplicateAccountException;
import com.budgetmanagementapp.exception.InvalidEmailException;
import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.exception.InvalidPhoneNumberException;
import com.budgetmanagementapp.exception.PasswordMismatchException;
import com.budgetmanagementapp.exception.PasswordNotSufficientException;
import com.budgetmanagementapp.exception.TransactionTypeNotFoundException;
import com.budgetmanagementapp.model.AccountRequestModel;
import com.budgetmanagementapp.model.CategoryRequestModel;
import com.budgetmanagementapp.model.DebtRequestModel;
import com.budgetmanagementapp.model.FeedbackRequestModel;
import com.budgetmanagementapp.model.TagRequestModel;
import com.budgetmanagementapp.model.TransactionRequestModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
import com.budgetmanagementapp.model.UpdateCategoryRequestModel;
import com.budgetmanagementapp.model.UpdateDebtRequestModel;
import com.budgetmanagementapp.model.UpdateTagRequestModel;
import com.budgetmanagementapp.model.UpdateTransactionRequestModel;
import com.budgetmanagementapp.model.UpdateTransferRequestModel;
import java.util.Arrays;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class CustomValidator {

    public static void validateUsernameFormat(String email) {
        if (!email.matches(
                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) {
            throw new InvalidEmailException(String.format(INVALID_EMAIL_MSG, email));
        }

    }

    public static void validatePhoneNumberFormat(String phoneNumber) {
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

    public static void validateCategoryModel(CategoryRequestModel requestModel) {
        if (Strings.isBlank(requestModel.getIcon())
                || Strings.isBlank(requestModel.getCategoryName())
                || Strings.isBlank(requestModel.getCategoryType())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateUpdateCategoryModel(UpdateCategoryRequestModel requestModel) {
        if (Strings.isBlank(requestModel.getIcon())
                || Strings.isBlank(requestModel.getCategoryName())
                || Strings.isBlank(requestModel.getCategoryId())
                || Strings.isBlank(requestModel.getCategoryType())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateTagRequestModel(TagRequestModel requestModel) {
        if (Strings.isBlank(requestModel.getTagName()) || Strings.isBlank(requestModel.getTagCategory())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateUpdateTagModel(UpdateTagRequestModel requestBody) {
        if (requestBody.getTagId() == null || requestBody.getTagId().isBlank()
                || requestBody.getTagName() == null || requestBody.getTagName().isBlank()
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
        if (Strings.isBlank(feedbackId)) {
            throw new InvalidModelException(INVALID_REQUEST_PARAM_MSG);
        }
    }

    public static void validateOutgoingModel(TransactionRequestModel requestBody) {
        if (Strings.isBlank(requestBody.getDateTime())
                || Objects.isNull(requestBody.getAmount())
                || Objects.isNull(requestBody.getDescription())
                || Strings.isBlank(requestBody.getSenderAccountId())
                || Strings.isBlank(requestBody.getCategoryId())
                || Objects.isNull(requestBody.getTagIds())
        ) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateTransactionType(String value) {
        if ((int) Arrays.stream(values()).filter(type -> type.name().equals(value.toUpperCase()))
                .count() == 0) {
            throw new TransactionTypeNotFoundException(String.format(TRANSACTION_TYPE_NOT_FOUND_MSG, value));
        }

    }

    public static void validateTransferModel(TransferRequestModel requestBody) {
        if (Strings.isBlank(requestBody.getCreationDateTime())
                || Objects.isNull(requestBody.getAmount())
                || Objects.isNull(requestBody.getDescription())
                || Strings.isBlank(requestBody.getAccountFromId())
                || Strings.isBlank(requestBody.getAccountToId())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }

        if (requestBody.getAccountFromId().equals(requestBody.getAccountToId())) {
            throw new DuplicateAccountException(TRANSFER_TO_SELF_MSG);
        }
    }

    public static void validateDebtModel(DebtRequestModel requestBody) {
        if (Strings.isBlank(requestBody.getCreationDateTime())
                || Objects.isNull(requestBody.getAmount())
                || Strings.isBlank(requestBody.getDescription())
                || Strings.isBlank(requestBody.getAccountId())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateUpdateInOutModel(UpdateTransactionRequestModel requestBody) {
        if (Strings.isBlank(requestBody.getTransactionId())
                || Strings.isBlank(requestBody.getDateTime())
                || Objects.isNull(requestBody.getAmount())
                || Strings.isBlank(requestBody.getDescription())
                || Strings.isBlank(requestBody.getAccountId())
                || Strings.isBlank(requestBody.getCategoryId())
                || Objects.isNull(requestBody.getTagIds())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateUpdateTransferModel(UpdateTransferRequestModel requestBody) {
        if (Strings.isBlank(requestBody.getTransactionId())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
        validateTransferModel(requestBody);
    }

    public static void validateUpdateDebtModel(UpdateDebtRequestModel requestBody) {
        if (Strings.isBlank(requestBody.getTransactionId())) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
        validateDebtModel(requestBody);
    }
}

