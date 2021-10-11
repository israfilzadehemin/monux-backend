package com.budgetmanagementapp.repository;

import com.budgetmanagementapp.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    default Optional<Banner> byBannerId(String bannerId) {
        return findByBannerId(bannerId);
    }

    default Optional<Banner> byKeyword(String keyword) {
        return findByKeyword(keyword);
    }

    Optional<Banner> findByBannerId(String bannerId);

    Optional<Banner> findByKeyword(String keyword);
}
