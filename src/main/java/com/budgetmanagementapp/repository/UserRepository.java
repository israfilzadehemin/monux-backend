package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> byUsernameAndStatus(String username, String status) {
        return findByUsernameIgnoreCaseAndStatus(username, status);
    }

    default Optional<User> byUsername(String username) {
        return findByUsernameIgnoreCase(username);
    }

    default Optional<User> byIdAndStatus(long id, String status) {
        return findByIdAndStatus(id, status);
    }

    default Optional<User> byOtp(Otp otp) {
        return findByOtp(otp);
    }

    Optional<User> findByUsernameIgnoreCaseAndStatus(String username, String status);
    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByIdAndStatus(long id, String status);

    Optional<User> findByOtp(Otp otp);
}
