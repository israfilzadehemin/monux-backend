package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.TransferTransaction;
import com.budgetmanagementapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
