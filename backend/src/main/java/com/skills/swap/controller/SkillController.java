package com.skills.swap.controller;

import com.skills.swap.model.Skill;
import com.skills.swap.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired private SkillRepository skillRepository;

    @GetMapping("/all")
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
