package com.budgetmanagementapp.utility;

import static com.budgetmanagementapp.utility.MsgConstant.CATEGORY_TYPE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_EMAIL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_INITIAL_ACCOUNT_MODEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_PHONE_NUMBER_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_REQUEST_MODEL_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.TRANSACTION_TYPE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.TransactionType.values;

import com.budgetmanagementapp.exception.CategoryTypeNotFoundException;
import com.budgetmanagementapp.exception.InvalidEmailException;
import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.exception.InvalidPhoneNumberException;
import com.budgetmanagementapp.exception.TransactionTypeNotFoundException;
import com.budgetmanagementapp.model.AccountRqModel;
import java.util.Arrays;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class CustomValidator {

    public static void validateEmailFormat(String email) {
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

    public static void validateAccountModel(AccountRqModel requestModel, boolean isInitialAccount) {

        if (isInitialAccount) {
            if (Strings.isBlank(requestModel.getUsername())
                    || Strings.isBlank(requestModel.getAccountName())
                    || Strings.isBlank(requestModel.getCurrency())
                    || Objects.isNull(requestModel.getBalance())
            ) {
                throw new InvalidModelException(INVALID_INITIAL_ACCOUNT_MODEL_MSG);
            }
        } else {
            if (Strings.isBlank(requestModel.getIcon())
                    || Strings.isBlank(requestModel.getAccountName())
                    || Strings.isBlank(requestModel.getAccountTypeName())
                    || Strings.isBlank(requestModel.getCurrency())
                    || Objects.isNull(requestModel.getBalance())
                    || Objects.isNull(requestModel.getAllowNegative())
                    || Objects.isNull(requestModel.getShowInSum())
            ) {
                throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
            }
        }

    }

    public static void validateCategoryType(String value) {
        if ((int) Arrays.stream(CategoryType.values()).filter(type -> type.name().equals(value.toUpperCase()))
                .count() == 0) {
            throw new CategoryTypeNotFoundException(String.format(CATEGORY_TYPE_NOT_FOUND_MSG, value));
        }
    }

    public static void validateTransactionType(String value) {
        if ((int) Arrays.stream(values())
                .filter(type -> type.name().equals(value.toUpperCase()))
                .count() == 0) {
            throw new TransactionTypeNotFoundException(String.format(TRANSACTION_TYPE_NOT_FOUND_MSG, value));
        }

    }

}

