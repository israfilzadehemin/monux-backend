package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.user.*;

import javax.mail.MessagingException;
import java.util.Optional;

public interface UserService {

    Optional<UserAuthModel> findAuthModelByUsername(String username);

    Optional<UserAuthModel> findById(long id);

    User findByUsername(String username);

    UserRsModel signup(UserRqModel signupRqModel) throws MessagingException;

    CreatePasswordRsModel createPassword(CreatePasswordRqModel passwordRequestModel);

    UserRsModel forgetPassword(String username) throws MessagingException;

    ResetPasswordRsModel resetPassword(String username, ResetPasswordRqModel requestBody);

    UserInfoRsModel getUserInfo(String username);

    UserInfoRsModel updateUserInfo(String username, UserRqModel requestBody);

    UserInfoRsModel updateUserPass(String username, UpdateUserPassRqModel requestBody);

    UserInfoRsModel updateUserLanguage(String username, String language);

    UserRsModel deleteUser(String username);
}
