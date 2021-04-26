package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.DebtTransaction;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtTransactionRepository extends JpaRepository<DebtTransaction, Long> {

    default Optional<DebtTransaction> byIdAndUser(String transactionId, User user) {
        return findByDebtTransactionIdAndUser(transactionId, user);
    }

    default List<DebtTransaction> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<DebtTransaction> findByDebtTransactionIdAndUser(String transactionId, User user);

    List<DebtTransaction> findAllByUser(User user);
}
