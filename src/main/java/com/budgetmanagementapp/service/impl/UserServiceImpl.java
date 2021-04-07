package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.model.UserAuthModel;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.UserService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserAuthModel> findByUsername(String username) {
        return userRepository.findByUsername(username).map(UserAuthModel::new);
    }

    @Override
    public Optional<UserAuthModel> findById(long id) {
        return userRepository.findById(id).map(UserAuthModel::new);
    }
}
