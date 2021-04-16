package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountIdAndUser(String accountId, User user);

    Optional<Account> findByNameAndUser(String name, User user);

    List<Account> findAllByUser(User user);
}
