package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Label;
import com.budgetmanagementapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {

    default Optional<Label> byNameAndUser(String labelName, User user) {
        return findByNameIgnoreCaseAndUser(labelName, user);
    }

    default Optional<Label> byIdAndUser(String labelId, User user) {
        return findByLabelIdAndUser(labelId, user);
    }

    default Optional<Label> byIdAndTypeAndUsers(String labelId, String type, List<User> users) {
        return findByLabelIdAndTypeIgnoreCaseAndUserIn(labelId, type, users);
    }

    default List<Label> allByUserOrGeneralUser(User user, User generalUser) {
        return findAllByUserOrUser(user, generalUser);
    }

    default List<Label> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<Label> findByNameIgnoreCaseAndUser(String labelName, User user);

    Optional<Label> findByLabelIdAndUser(String labelId, User user);

    Optional<Label> findByLabelIdAndTypeIgnoreCaseAndUserIn(String labelId, String type, List<User> users);

    List<Label> findAllByUserOrUser(User user, User generalUser);

    List<Label> findAllByUser(User user);
}
