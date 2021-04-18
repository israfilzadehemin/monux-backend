package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.CustomTag;
import com.budgetmanagementapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomTagRepository extends JpaRepository<CustomTag, Long> {

    default Optional<CustomTag> byNameAndUser(String name, User user) {
        return findByNameIgnoreCaseAndUser(name, user);
    }

    default Optional<CustomTag> byIdAndUser(String id, User user) {
        return findByCustomTagIdAndUser(id, user);
    }

    default List<CustomTag> allByUser(User user) {
        return findAllByUser(user);
    }

    Optional<CustomTag> findByNameIgnoreCaseAndUser(String name, User user);

    Optional<CustomTag> findByCustomTagIdAndUser(String id, User user);

    List<CustomTag> findAllByUser(User user);
}
