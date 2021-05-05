package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    default Optional<Role> byName(String roleName) {
        return findByNameIgnoreCase(roleName);
    }

    Optional<Role> findByNameIgnoreCase(String name);
}
