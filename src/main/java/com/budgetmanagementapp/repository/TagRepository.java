package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Tag;
import com.budgetmanagementapp.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    default Optional<Tag> byId(String tagId) {
        return findByTagId(tagId);
    }

    default Optional<Tag> byNameAndUser(String tagName, User user) {
        return findByNameAndUser(tagName, user);
    }

    Optional<Tag> findByTagId(String tagId);

    Optional<Tag> findByNameAndUser(String tagName, User user);
}
