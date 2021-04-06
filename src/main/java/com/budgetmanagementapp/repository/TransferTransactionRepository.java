package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.TransferTemplate;
import com.budgetmanagementapp.entity.TransferTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {
}
