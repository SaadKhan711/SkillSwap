package com.skills.swap.repository;

import com.skills.swap.model.Skill;
import com.skills.swap.model.SkillOffering;
import com.skills.swap.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SkillOfferingRepository extends JpaRepository<SkillOffering, Long> {

    @Override
    @EntityGraph(attributePaths = {"user", "skill"})
    Optional<SkillOffering> findById(Long id);

    List<SkillOffering> findBySkill(Skill skill);

    List<SkillOffering> findBySkillIn(Set<Skill> skills);

    @Query("SELECT so FROM SkillOffering so JOIN FETCH so.user JOIN FETCH so.skill")
    List<SkillOffering> findAllWithDetails();

    long countByUser(User user);
    @Transactional
    void deleteByUserAndSkill(User user, Skill skill);
}
