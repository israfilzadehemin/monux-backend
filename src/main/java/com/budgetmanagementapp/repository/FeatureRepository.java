package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    default List<Feature> allByFeatureIds(List<String> featureIds) {
        return findAllByFeatureIdIn(featureIds);
    }

    List<Feature> findAllByFeatureIdIn(List<String> featureIds);
}
