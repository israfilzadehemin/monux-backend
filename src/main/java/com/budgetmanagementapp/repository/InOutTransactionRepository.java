package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.InOutTransaction;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InOutTransactionRepository extends JpaRepository<InOutTransaction, Long> {

    default Optional<InOutTransaction> byIdAndUser(String transactionId, User user) {
        return findByInOutTransactionIdAndUser(transactionId, user);
    }

    default List<InOutTransaction> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<InOutTransaction> findByInOutTransactionIdAndUser(String transactionId, User user);

    List<InOutTransaction> findAllByUser(User user);
}
