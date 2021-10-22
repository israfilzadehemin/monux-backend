package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Optional<Service> findByServiceId(String serviceId);
}
