package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Currency;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    default Optional<Currency> byName(String currencyName) {
        return findByNameIgnoreCase(currencyName);
    }

    Optional<Currency> findByNameIgnoreCase(String name);
}
