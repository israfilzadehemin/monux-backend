package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.user.CreatePasswordRqModel;
import com.budgetmanagementapp.model.user.CreatePasswordRsModel;
import com.budgetmanagementapp.model.user.ResetPasswordRqModel;
import com.budgetmanagementapp.model.user.ResetPasswordRsModel;
import com.budgetmanagementapp.model.user.SignupRqModel;
import com.budgetmanagementapp.model.user.UserAuthModel;
import com.budgetmanagementapp.model.user.UserRsModel;
import java.util.Optional;
import javax.mail.MessagingException;

public interface UserService {

    Optional<UserAuthModel> findAuthModelByUsername(String username);

    Optional<UserAuthModel> findById(long id);

    User findByUsername(String username);

    UserRsModel signup(SignupRqModel signupRqModel) throws MessagingException;

    CreatePasswordRsModel createPassword(CreatePasswordRqModel passwordRequestModel);

    ResetPasswordRsModel forgetPassword(String username, ResetPasswordRqModel requestBody) throws MessagingException;

    ResetPasswordRsModel resetPassword(String username, ResetPasswordRqModel requestBody);
}
