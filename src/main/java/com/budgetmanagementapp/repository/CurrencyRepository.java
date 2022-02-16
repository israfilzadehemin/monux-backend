package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    default Optional<Currency> byName(String currencyName) {
        return findByNameIgnoreCase(currencyName);
    }

    Optional<Currency> findByNameIgnoreCase(String name);
}
