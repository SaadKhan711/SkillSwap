package com.skills.swap.repository;

import com.skills.swap.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    
    // This method is still needed by our SkillService
    Optional<Skill> findByNameIgnoreCase(String name);


}
