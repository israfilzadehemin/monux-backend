package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    default Optional<Tag> byId(String tagId) {
        return findByTagId(tagId);
    }

    Optional<Tag> findByTagId(String tagId);
}
