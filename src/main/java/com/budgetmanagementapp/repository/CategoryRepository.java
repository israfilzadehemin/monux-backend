package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Optional<Category> byNameAndUser(String name, User user) {
        return findByNameIgnoreCaseAndUser(name, user);
    }

    default Optional<Category> byIdAndUser(String categoryId, User user) {
        return findByCategoryIdAndUser(categoryId, user);
    }

    default Optional<Category> allByUser(User user) {
        return findAllByUser(user);
    }

    default Optional<Category> allByUserOrGeneralUser(User user, User generalUser) {
        return findAllByUserOrUser(user, generalUser);
    }

    default Optional<Category> byIdAndTypeAndUsers(String categoryId, String type, List<User> users) {
        return findByCategoryIdAndTypeAndUserIn(categoryId, type,users);
    }

    Optional<Category> findByNameIgnoreCaseAndUser(String name, User user);

    Optional<Category> findByCategoryIdAndUser(String categoryId, User user);

    Optional<Category> findAllByUser(User user);

    Optional<Category> findAllByUserOrUser(User user, User generalUser);

    Optional<Category> findByCategoryIdAndTypeAndUserIn(String categoryId, String type, List<User> users);
}
