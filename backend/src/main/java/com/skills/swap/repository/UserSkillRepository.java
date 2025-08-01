package com.skills.swap.repository;

import com.skills.swap.model.User;
import com.skills.swap.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    // Counts how many skills a specific user has on their profile.
    long countByUser(User user);

    // Finds all skills for a specific user.
    List<UserSkill> findByUser(User user);
}
