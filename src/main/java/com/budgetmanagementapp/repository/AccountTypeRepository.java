package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {
}
