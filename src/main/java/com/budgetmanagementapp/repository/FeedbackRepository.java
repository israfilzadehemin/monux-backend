package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Feedback;
import com.budgetmanagementapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    default List<Feedback> allByUser(User user) {
        return findAllByUser(user);
    }

    default Optional<Feedback> byIdAndUser(String feedbackId, User user) {
        return findByFeedbackIdAndUser(feedbackId, user);
    }

    List<Feedback> findAllByUser(User user);

    Optional<Feedback> findByFeedbackIdAndUser(String feedbackId, User user);

}
