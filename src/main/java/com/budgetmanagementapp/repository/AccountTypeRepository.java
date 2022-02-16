package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    default Optional<AccountType> byName(String accountTypeName) {
        return findByAccountTypeNameIgnoreCase(accountTypeName);
    }

    Optional<AccountType> findByAccountTypeNameIgnoreCase(String name);
}
