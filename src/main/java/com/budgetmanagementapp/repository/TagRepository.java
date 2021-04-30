package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    default Optional<Tag> byNameAndUser(String tagName, User user) {
        return findByNameIgnoreCaseAndUser(tagName, user);
    }

    default Optional<Tag> byIdAndUser(String tagId, User user) {
        return findByTagIdAndUser(tagId, user);
    }

    default Optional<Tag> byIdAndTypeAndUsers(String tagId, String type, List<User> users) {
        return findByTagIdAndTypeAndUserIn(tagId, type, users);
    }

    default List<Tag> allByUserOrGeneralUser(User user, User generalUser) {
        return findAllByUserOrUser(user, generalUser);
    }

    default List<Tag> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<Tag> findByNameIgnoreCaseAndUser(String tagName, User user);

    Optional<Tag> findByTagIdAndUser(String tagId, User user);

    Optional<Tag> findByTagIdAndTypeAndUserIn(String tagId, String type, List<User> users);

    List<Tag> findAllByUserOrUser(User user, User generalUser);

    List<Tag> findAllByUser(User user);
}
