package com.budgetmanagementapp.utility;

import static com.budgetmanagementapp.utility.MsgConstant.INVALID_EMAIL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_INITIAL_ACCOUNT_MODEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_PHONE_NUMBER_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_REQUEST_MODEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_REQUEST_PARAM_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_MISMATCH_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.PASSWORD_NOT_SUFFICIENT_MSG;

import com.budgetmanagementapp.exception.InvalidEmailException;
import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.exception.InvalidPhoneNumberException;
import com.budgetmanagementapp.exception.PasswordMismatchException;
import com.budgetmanagementapp.exception.PasswordNotSufficientException;
import com.budgetmanagementapp.model.AccountRequestModel;
import com.budgetmanagementapp.model.UpdateAccountModel;
import com.budgetmanagementapp.model.UpdateBalanceModel;
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

    public static void validateAccountModel(AccountRequestModel accountRequestModel, boolean isInitialAccount) {

        if (isInitialAccount) {
            if (accountRequestModel.getUsername() == null || accountRequestModel.getUsername().isBlank()
                    || accountRequestModel.getAccountName() == null || accountRequestModel.getAccountName().isBlank()
                    || accountRequestModel.getCurrency() == null || accountRequestModel.getCurrency().isBlank()
                    || accountRequestModel.getBalance() == null
            ) {
                throw new InvalidModelException(INVALID_INITIAL_ACCOUNT_MODEL_MSG);
            }
        } else {
            if (accountRequestModel.getIcon() == null || accountRequestModel.getIcon().isBlank()
                    || accountRequestModel.getAccountName() == null || accountRequestModel.getAccountName().isBlank()
                    || accountRequestModel.getAccountTypeName() == null
                    || accountRequestModel.getAccountTypeName().isBlank() || accountRequestModel.getCurrency() == null
                    || accountRequestModel.getCurrency().isBlank() || accountRequestModel.getBalance() == null
                    || accountRequestModel.getAllowNegative() == null || accountRequestModel.getShowInSum() == null
            ) {
                throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
            }
        }

    }

    public static void validateUpdateAccountModel(UpdateAccountModel accountModel) {
        if (accountModel.getAccountId() == null || accountModel.getAccountId().isBlank()
                || accountModel.getNewAccountName() == null || accountModel.getNewAccountName().isBlank()
                || accountModel.getIcon() == null || accountModel.getIcon().isBlank()
                || accountModel.getAccountTypeName() == null || accountModel.getAccountTypeName().isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }

    public static void validateAccountId(String accountId) {
        if (accountId == null || accountId.isBlank()) {
            throw new InvalidModelException(INVALID_REQUEST_PARAM_MSG);
        }
    }

    public static void validateUpdateBalanceModel(UpdateBalanceModel balanceModel) {
        if (balanceModel.getAccountId() == null || balanceModel.getAccountId().isBlank()
                || balanceModel.getBalance() == null) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }
    }
}
