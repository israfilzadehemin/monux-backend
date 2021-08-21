package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.*;

import java.util.Optional;
import javax.mail.MessagingException;

public interface UserService {

    Optional<UserAuthModel> findAuthModelByUsername(String username);

    Optional<UserAuthModel> findById(long id);

    User findByUsername(String username);

    UserRsModel signup(SignupRqModel username) throws MessagingException;

    CreatePasswordRsModel createPassword(CreatePasswordRqModel passwordRequestModel);

    ResetPasswordRsModel resetPassword(String username, ResetPasswordRqModel passwordRequestModel);
}
