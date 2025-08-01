package com.skills.swap.controller;

import com.skills.swap.dto.LinkRequest;
import com.skills.swap.dto.SkillOfferingDTO;
import com.skills.swap.model.Skill;
import com.skills.swap.model.SkillOffering;
import com.skills.swap.model.User;
import com.skills.swap.repository.SkillOfferingRepository;
import com.skills.swap.repository.SkillRepository;
import com.skills.swap.repository.UserRepository;
import com.skills.swap.service.BadgeService;
import com.skills.swap.service.DataMapper;
import com.skills.swap.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/offerings")
public class SkillOfferingController {

    @Autowired private SkillOfferingRepository offeringRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private TransactionService transactionService;
    @Autowired private DataMapper dataMapper;
    @Autowired private BadgeService badgeService;

    @PostMapping
    public ResponseEntity<SkillOffering> createOffering(@RequestBody LinkRequest linkRequest) {
        User user = userRepository.findById(linkRequest.getUserId()).orElse(null);
        Skill skill = skillRepository.findById(linkRequest.getSkillId()).orElse(null);
        if (user == null || skill == null) return ResponseEntity.badRequest().build();
        SkillOffering newOffering = new SkillOffering();
        newOffering.setUser(user);
        newOffering.setSkill(skill);
        return transactionService.createAndFetchOffering(newOffering)
                .map(offering -> {
                    badgeService.checkAndAwardBadges(offering.getUser());
                    return ResponseEntity.ok(offering);
                })
                .orElse(ResponseEntity.internalServerError().build());
    }
    
    @GetMapping
    public List<SkillOfferingDTO> getAllOfferings() {
        return offeringRepository.findAllWithDetails().stream()
                .map(dataMapper::toSkillOfferingDTO)
                .collect(Collectors.toList());
    }
}