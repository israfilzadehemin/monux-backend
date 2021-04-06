package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.DebtTemplate;
import com.budgetmanagementapp.entity.DebtTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtTransactionRepository extends JpaRepository<DebtTransaction, Long> {
}
