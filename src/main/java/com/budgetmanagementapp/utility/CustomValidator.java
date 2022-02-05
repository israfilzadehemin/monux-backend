package com.budgetmanagementapp.utility;

import static com.budgetmanagementapp.utility.Constant.STATUS_NEW;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static com.budgetmanagementapp.utility.TransactionType.values;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.exception.*;
import com.budgetmanagementapp.model.account.AccountRqModel;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomValidator {

    public static void checkOtpAvailability(Otp otp) {
        if (otp.getDateTime().isBefore(LocalDateTime.now().minusMinutes(2)) || !otp.getStatus().equals(STATUS_NEW)) {
            throw new ExpiredOtpException(EXPIRED_OTP_MSG);
        }
    }

    public static void validateUsername(String username) {
        if (username.contains("@")) {
            validateEmailFormat(username);
        } else {
            validatePhoneNumberFormat(username);
        }
    }

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
            if (Strings.isBlank(requestModel.getAccountName())
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

    public static void validateFullName(String fullName) {
        if (fullName.trim().split(" ").length < 2) {
            throw new FullNameFormatException(FULL_NAME_WRONG_FORMAT_MSG);
        }
    }

}

