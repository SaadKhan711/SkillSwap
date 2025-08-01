package com.skills.swap.repository;

import com.skills.swap.model.Skill;
import com.skills.swap.model.SkillRequest;
import com.skills.swap.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SkillRequestRepository extends JpaRepository<SkillRequest, Long> {

    @Override
    @EntityGraph(attributePaths = {"user", "skill"})
    Optional<SkillRequest> findById(Long id);

    List<SkillRequest> findByUserId(Long userId);

    List<SkillRequest> findByUserIdAndStatus(Long userId, String status);
    List<SkillRequest> findBySkillInAndStatus(List<Skill> skills, String status);
    List<SkillRequest> findByUserInAndStatus(List<User> users, String status);

    @Query("SELECT sr FROM SkillRequest sr JOIN FETCH sr.user JOIN FETCH sr.skill")
    List<SkillRequest> findAllWithDetails();

    long countByUserAndStatus(User user, String status);
}
