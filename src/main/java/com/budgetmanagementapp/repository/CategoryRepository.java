package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Optional<Category> byName(String name) {
        return findByNameIgnoreCase(name);
    }

    Optional<Category> findByNameIgnoreCase(String name);
}
