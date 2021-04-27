package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
