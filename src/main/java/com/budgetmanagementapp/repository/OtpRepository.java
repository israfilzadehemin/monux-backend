package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtp(String otp);
}
