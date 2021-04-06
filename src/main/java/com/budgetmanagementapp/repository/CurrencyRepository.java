package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
