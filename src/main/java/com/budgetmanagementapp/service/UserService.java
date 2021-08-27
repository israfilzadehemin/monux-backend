package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.CreatePasswordRqModel;
import com.budgetmanagementapp.model.CreatePasswordRsModel;
import com.budgetmanagementapp.model.ResetPasswordRqModel;
import com.budgetmanagementapp.model.ResetPasswordRsModel;
import com.budgetmanagementapp.model.SignupRqModel;
import com.budgetmanagementapp.model.UserAuthModel;
import com.budgetmanagementapp.model.UserRsModel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;

public interface UserService {

    Optional<UserAuthModel> findAuthModelByUsername(String username);

    Optional<UserAuthModel> findById(long id);

    User findByUsername(String username);

    UserRsModel signup(SignupRqModel username) throws MessagingException;

    CreatePasswordRsModel createPassword(CreatePasswordRqModel passwordRequestModel);

    ResetPasswordRsModel forgetPassword(String username, ResetPasswordRqModel requestBody)
            throws MessagingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException;

    ResetPasswordRsModel resetPassword(String username, ResetPasswordRqModel requestBody);
}
