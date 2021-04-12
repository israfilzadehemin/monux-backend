package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndStatus(String username, String status);
    Optional<User> findByUsername(String username);

    Optional<User> findByIdAndStatus(long id, String status);

    Optional<User> findByOtp(Otp otp);
}
