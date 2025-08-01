package com.skills.swap.repository;

import com.skills.swap.model.DismissedRecommendation;
import com.skills.swap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DismissedRecommendationRepository extends JpaRepository<DismissedRecommendation, Long> {
    
    // This custom method will find all dismissals for a specific user,
    // which is needed by the recommendation logic.
    List<DismissedRecommendation> findByUser(User user);
}
