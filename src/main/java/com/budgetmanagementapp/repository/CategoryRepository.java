package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Category;
import com.budgetmanagementapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Optional<Category> byNameAndUser(String name, User user) {
        return findByNameIgnoreCaseAndUser(name, user);
    }

    default Optional<Category> byIdAndUser(String categoryId, User user) {
        return findByCategoryIdAndUser(categoryId, user);
    }

    default List<Category> allByUser(User user) {
        return findAllByUser(user);
    }

    default List<Category> allByUserOrGeneralUser(User user, User generalUser) {
        return findAllByUserOrUser(user, generalUser);
    }

    default Optional<Category> byIdAndTypeAndUsers(String categoryId, String type, List<User> users) {
        return findByCategoryIdAndTypeAndUserIn(categoryId, type, users);
    }

    Optional<Category> findByNameIgnoreCaseAndUser(String name, User user);

    Optional<Category> findByCategoryIdAndUser(String categoryId, User user);

    List<Category> findAllByUser(User user);

    List<Category> findAllByUserOrUser(User user, User generalUser);

    Optional<Category> findByCategoryIdAndTypeAndUserIn(String categoryId, String type, List<User> users);
}
