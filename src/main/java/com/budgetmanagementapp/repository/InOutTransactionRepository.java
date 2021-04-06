package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.InOutTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InOutTransactionRepository extends JpaRepository<InOutTransaction, Long> {
}
