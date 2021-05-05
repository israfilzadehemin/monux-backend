package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.AccountType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    default Optional<AccountType> byName(String accountTypeName) {
        return findByAccountTypeNameIgnoreCase(accountTypeName);
    }

    Optional<AccountType> findByAccountTypeNameIgnoreCase(String name);
}
