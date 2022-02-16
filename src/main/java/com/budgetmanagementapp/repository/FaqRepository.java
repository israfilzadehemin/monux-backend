package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaqRepository extends JpaRepository<Faq, Long> {

    default Optional<Faq> byId(String faqId) {
        return findByFaqId(faqId);
    }

    Optional<Faq> findByFaqId(String faqId);
}
