package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefinitionRepository extends JpaRepository<Definition, Long> {
}
