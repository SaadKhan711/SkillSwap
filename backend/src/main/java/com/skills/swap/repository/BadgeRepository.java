package com.skills.swap.repository;

import com.skills.swap.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findByName(String name);
}
