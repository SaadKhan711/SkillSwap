package com.skills.swap.controller;

import com.skills.swap.dto.AddSkillProfileRequestDTO;
import com.skills.swap.model.Skill;
import com.skills.swap.model.User;
import com.skills.swap.model.UserSkill;
import com.skills.swap.repository.SkillOfferingRepository;
import com.skills.swap.repository.UserRepository;
import com.skills.swap.repository.UserSkillRepository;
import com.skills.swap.service.BadgeService;
import com.skills.swap.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserSkillController {

    @Autowired private UserSkillRepository userSkillRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SkillOfferingRepository skillOfferingRepository;
    @Autowired private BadgeService badgeService;
    @Autowired private SkillService skillService;

    @PostMapping("/add-skill")
    public ResponseEntity<UserSkill> addSkillToProfile(@RequestBody AddSkillProfileRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId()).orElse(null);
        if (user == null) return ResponseEntity.badRequest().build();
        Skill skill = skillService.findOrCreateSkill(requestDTO.getSkillName());
        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setProficiency(requestDTO.getProficiency());
        userSkillRepository.save(userSkill);
        badgeService.checkAndAwardBadges(user);
        return ResponseEntity.ok(userSkill);
    }

    @DeleteMapping("/skill/{userSkillId}")
    public ResponseEntity<Void> deleteSkillFromProfile(@PathVariable Long userSkillId) {
        UserSkill userSkill = userSkillRepository.findById(userSkillId).orElse(null);
        if (userSkill == null) return ResponseEntity.notFound().build();
        User user = userSkill.getUser();
        Skill skill = userSkill.getSkill();
        skillOfferingRepository.deleteByUserAndSkill(user, skill);
        userSkillRepository.delete(userSkill);
        return ResponseEntity.ok().build();
    }
}

