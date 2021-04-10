package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.UserAuthModel;
import java.util.Optional;

public interface UserService  {

    Optional<UserAuthModel> findByUsername(String username);
    Optional<UserAuthModel> findById(long id);
}
