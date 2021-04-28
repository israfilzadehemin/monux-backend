package com.budgetmanagementapp.service;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.CreatePasswordRequestModel;
import com.budgetmanagementapp.model.CreatePasswordResponseModel;
import com.budgetmanagementapp.model.SignupRequestModel;
import com.budgetmanagementapp.model.UserAuthModel;
import com.budgetmanagementapp.model.UserResponseModel;
import java.util.Optional;
import javax.mail.MessagingException;

public interface UserService {

    Optional<UserAuthModel> findAuthModelByUsername(String username);

    Optional<UserAuthModel> findById(long id);

    User findByUsername(String username);

    UserResponseModel signup(SignupRequestModel username) throws MessagingException;

    CreatePasswordResponseModel createPassword(CreatePasswordRequestModel passwordRequestModel);
}
