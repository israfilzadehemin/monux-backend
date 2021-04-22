package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Optional<Category> byName(String name) {
        return findByNameIgnoreCase(name);
    }

    default Optional<Category> byIdAndType(String categoryId, String categoryType) {
        return findByCategoryIdAndType(categoryId, categoryType);
    }

    Optional<Category> findByCategoryIdAndType(String categoryId, String type);

    Optional<Category> findByNameIgnoreCase(String name);
}
