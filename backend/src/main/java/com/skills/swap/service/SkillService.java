package com.skills.swap.service;

import com.skills.swap.model.Skill;
import com.skills.swap.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Transactional
    public Skill findOrCreateSkill(String name) {
        String normalizedName = name.trim();
        
        return skillRepository.findByNameIgnoreCase(normalizedName)
            .orElseGet(() -> {
                Skill newSkill = new Skill();
                newSkill.setName(normalizedName);
                return skillRepository.save(newSkill);
            });
    }
}
