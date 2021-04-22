package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.CustomCategory;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {
    default Optional<CustomCategory> byNameAndUser(String name, User user) {
        return findByNameIgnoreCaseAndUser(name, user);
    }

    default Optional<CustomCategory> byIdAndUser(String id, User user) {
        return findByCustomCategoryIdAndUser(id, user);
    }

    default Optional<CustomCategory> byIdAndUserAndType(String id, User user, String categoryType) {
        return findByCustomCategoryIdAndUserAndType(id, user, categoryType);
    }

    default List<CustomCategory> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<CustomCategory> findByNameIgnoreCaseAndUser(String name, User user);

    Optional<CustomCategory> findByCustomCategoryIdAndUser(String categoryId, User user);

    Optional<CustomCategory> findByCustomCategoryIdAndUserAndType(String categoryId,
                                                                  User user,
                                                                  String categoryType);

    List<CustomCategory> findAllByUser(User user);
}
