package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Fag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FagRepository extends JpaRepository<Fag, Long> {

    default Optional<Fag> byId(String fagId){
        return findByFagId(fagId);
    }

    Optional<Fag> findByFagId(String fagId);
}
