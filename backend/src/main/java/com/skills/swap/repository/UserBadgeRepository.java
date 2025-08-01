package com.skills.swap.repository;

import com.skills.swap.model.Badge;
import com.skills.swap.model.User;
import com.skills.swap.model.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    List<UserBadge> findByUser(User user);
    boolean existsByUserAndBadge(User user, Badge badge);
}
