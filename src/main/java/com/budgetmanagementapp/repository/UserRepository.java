package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.utility.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> byUsernameAndStatus(String username, UserStatus status) {
        return findByUsernameIgnoreCaseAndStatus(username, status);
    }

    default Optional<User> byUsername(String username) {
        return findByUsernameIgnoreCase(username);
    }

    default Optional<User> byIdAndStatus(long id, UserStatus status) {
        return findByIdAndStatus(id, status);
    }

    default Optional<User> byOtp(Otp otp) {
        return findByOtp(otp);
    }

    Optional<User> findByUsernameIgnoreCaseAndStatus(String username, UserStatus status);

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByIdAndStatus(long id, UserStatus status);

    Optional<User> findByOtp(Otp otp);
}
