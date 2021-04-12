package com.budgetmanagementapp.utility;

import static com.budgetmanagementapp.utility.MsgConstant.INVALID_REQUEST_MODEL_MSG;

import com.budgetmanagementapp.exception.InvalidEmailException;
import com.budgetmanagementapp.exception.InvalidModelException;
import com.budgetmanagementapp.exception.PasswordMismatchException;
import com.budgetmanagementapp.exception.PasswordNotSufficientException;
import com.budgetmanagementapp.model.CreateAccountModel;
import org.springframework.stereotype.Component;

@Component
public class CustomValidator {

    public static void validateEmailFormat(String email) {
        if (email == null) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }

        if (!email.matches(
                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}")) {
            throw new InvalidEmailException(
                    String.format("Email '%s' does not follow valid email format rules", email));
        }

    }

    public static void validatePassword(String password, String confirmPassword) {

        if (password == null || confirmPassword == null) {
            throw new InvalidModelException(INVALID_REQUEST_MODEL_MSG);
        }

        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Password and confirm password must be the same");
        }

        if (password.length() < 5) {
            throw new PasswordNotSufficientException("Password must be at least 5 symbols long");
        }
    }

    public static void validateInitialAccount(CreateAccountModel createAccountModel) {
        if (createAccountModel.getUsername() == null || createAccountModel.getUsername().isBlank()
                || createAccountModel.getAccountName() == null || createAccountModel.getAccountName().isBlank()
                || createAccountModel.getCurrency() == null || createAccountModel.getCurrency().isBlank()
                || createAccountModel.getBalance() == null
        ) {
            throw new InvalidModelException(
                    " 'username', 'accountName', 'currency' and 'balance' fields are mandatory for initial account");
        }
    }
}
