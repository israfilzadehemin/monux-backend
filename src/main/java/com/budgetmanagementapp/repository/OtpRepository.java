package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Notification;
import com.budgetmanagementapp.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp, Long> {
}
