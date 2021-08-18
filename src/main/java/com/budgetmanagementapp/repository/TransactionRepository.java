package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Account;
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

    default List<Transaction> allByUserAndSenderAccount(User user, Account account) {
        return findAllByUserAndSenderAccount(user, account);
    }

    default List<Transaction> allByUserAndReceiverAccount(User user, Account account) {
        return findAllByUserAndReceiverAccount(user, account);
    }

    default Page<Transaction> lastByUser(User user, Pageable pageable) {
        return findAllByUser(user, pageable);
    }

    default Page<Transaction> lastByUserAndSenderAccount(User user, Account account, Pageable pageable) {
        return findAllByUserAndSenderAccount(user, account, pageable);
    }

    default Page<Transaction> lastByUserAndReceiverAccount(User user, Account account, Pageable pageable) {
        return findAllByUserAndReceiverAccount(user, account, pageable);
    }

    default List<Transaction> allByUserAndIdList(User user, List<String> transactionIds) {
        return findByUserAndTransactionIdIn(user, transactionIds);
    }

    default void deleteById(User user, String transactionId) {
        deleteTransactionByUserAndTransactionId(user, transactionId);
    }

    Optional<Transaction> findByTransactionIdAndUser(String transactionId, User user);

    List<Transaction> findAllByUser(User user);

    List<Transaction> findAllByUserAndSenderAccount(User user, Account account);

    List<Transaction> findAllByUserAndReceiverAccount(User user, Account account);

    Page<Transaction> findAllByUser(User user, Pageable pageable);

    Page<Transaction> findAllByUserAndSenderAccount(User user, Account account, Pageable pageable);

    Page<Transaction> findAllByUserAndReceiverAccount(User user, Account account, Pageable pageable);

    List<Transaction> findByUserAndTransactionIdIn(User user, List<String> transactionIds);

    void deleteTransactionByUserAndTransactionId(User user, String transactionId);

}
