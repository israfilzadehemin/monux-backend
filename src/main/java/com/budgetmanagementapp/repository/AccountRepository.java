package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Account;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    default Optional<Account> byIdAndUser(String accountId, User user) {
        return findByAccountIdAndUser(accountId, user);
    }

    default Optional<Account> byNameAndUser(String accountName, User user) {
        return findByNameIgnoreCaseAndUser(accountName, user);
    }

    default Optional<Account> byNameAndUserAndIdNot(String accountName, User user, String accountId) {
        return findByNameIgnoreCaseAndUserAndAccountIdNot(accountName, user, accountId);
    }

    default List<Account> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<Account> findByAccountIdAndUser(String accountId, User user);

    Optional<Account> findByNameIgnoreCaseAndUser(String name, User user);

    Optional<Account> findByNameIgnoreCaseAndUserAndAccountIdNot(String name, User user, String accountId);

    List<Account> findAllByUser(User user);
}
