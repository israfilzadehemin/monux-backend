package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    default Optional<Transaction> byIdAndUser(String transactionId, User user) {
        return findByTransactionIdAndUser(transactionId, user);
    }

    default List<Transaction> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<Transaction> findByTransactionIdAndUser(String transactionId, User user);

    List<Transaction> findAllByUser(User user);
}
