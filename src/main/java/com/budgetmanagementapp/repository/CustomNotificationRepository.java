package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.CustomNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomNotificationRepository extends JpaRepository<CustomNotification, Long> {
}
