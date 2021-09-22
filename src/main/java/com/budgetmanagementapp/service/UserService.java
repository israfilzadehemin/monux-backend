package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.user.*;

import java.util.Optional;
import javax.mail.MessagingException;

public interface UserService {

    Optional<UserAuthModel> findAuthModelByUsername(String username);

    Optional<UserAuthModel> findById(long id);

    User findByUsername(String username);

    UserRsModel signup(SignupRqModel signupRqModel) throws MessagingException;

    CreatePasswordRsModel createPassword(CreatePasswordRqModel passwordRequestModel);

    UserRsModel forgetPassword(String username) throws MessagingException;

    ResetPasswordRsModel resetPassword(String username, ResetPasswordRqModel requestBody);

    UserInfoRsModel userInfo(String username);
}
