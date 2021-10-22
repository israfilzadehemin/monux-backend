package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    default List<Feature> allByFeatureIds(List<String> featureIds) {
        return findAllByFeatureIdIn(featureIds);
    }

    default Optional<Feature> byFeatureId(String featureId) {
        return findByFeatureId(featureId);
    }

    List<Feature> findAllByFeatureIdIn(List<String> featureIds);

    Optional<Feature> findByFeatureId(String featureId);
}
