package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.TransferTransaction;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferTransactionRepository extends JpaRepository<TransferTransaction, Long> {

    default Optional<TransferTransaction> byIdAndUser(String transactionId, User user) {
        return findByTransferTransactionIdAndUser(transactionId, user);
    }

    default List<TransferTransaction> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<TransferTransaction> findByTransferTransactionIdAndUser(String transactionId, User user);

    List<TransferTransaction> findAllByUser(User user);
}
