package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Transaction;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    default Optional<Transaction> byIdAndUser(String transactionId, User user) {
        return findByTransactionIdAndUser(transactionId, user);
    }

    default List<Transaction> allByUser(User user) {
        return findAllByUser(user);
    }

    default Page<Transaction> lastByUser(User user, Pageable pageable) {
        return findAllByUser(user, pageable);
    }
    Optional<Transaction> findByTransactionIdAndUser(String transactionId, User user);

    List<Transaction> findAllByUser(User user);

    Page<Transaction> findAllByUser(User user, Pageable pageable);



}
