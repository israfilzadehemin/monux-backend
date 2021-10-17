package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefinitionRepository extends JpaRepository<Definition, Long> {

    default Optional<Definition> byDefinitionId(String definitionId) {
        return findByDefinitionId(definitionId);
    }

    Optional<Definition> findByDefinitionId(String definitionId);
}
