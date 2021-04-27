package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    default Optional<Tag> byNameAndUser(String tagName, User user) {
        return findByNameAndUser(tagName, user);
    }

    default List<Tag> allByUserOrGeneralUser(User user, User generalUser) {
        return findAllByUserOrUser(user, generalUser);
    }

    default Optional<Tag> byIdAndUser(String tagId, User user) {
        return findByTagIdAndUser(tagId, user);
    }

    default List<Tag> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<Tag> findByNameAndUser(String tagName, User user);

    Optional<Tag> findByTagIdAndUser(String tagId, User user);

    List<Tag> findAllByUserOrUser(User user, User generalUser);

    List<Tag> findAllByUser(User user);
}
