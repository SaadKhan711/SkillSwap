package com.skills.swap.controller;

import com.skills.swap.dto.LinkRequest;
import com.skills.swap.dto.SkillRequestDTO;
import com.skills.swap.model.Skill;
import com.skills.swap.model.SkillRequest;
import com.skills.swap.model.User;
import com.skills.swap.repository.SkillRepository;
import com.skills.swap.repository.SkillRequestRepository;
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
@RequestMapping("/api/requests")
public class SkillRequestController {

    @Autowired private SkillRequestRepository requestRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private SkillRepository skillRepository;
    @Autowired private TransactionService transactionService;
    @Autowired private DataMapper dataMapper;
    @Autowired private BadgeService badgeService;

    @PostMapping
    public ResponseEntity<SkillRequest> createRequest(@RequestBody LinkRequest linkRequest) {
        User user = userRepository.findById(linkRequest.getUserId()).orElse(null);
        Skill skill = skillRepository.findById(linkRequest.getSkillId()).orElse(null);
        if (user == null || skill == null) return ResponseEntity.badRequest().build();
        SkillRequest newRequest = new SkillRequest();
        newRequest.setUser(user);
        newRequest.setSkill(skill);
        return transactionService.createAndFetchRequest(newRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().build());
    }
    
    @GetMapping
    public List<SkillRequestDTO> getAllRequests() {
        return requestRepository.findAllWithDetails().stream()
                .map(dataMapper::toSkillRequestDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/{requestId}/complete")
    public ResponseEntity<SkillRequest> completeRequest(@PathVariable Long requestId) {
        SkillRequest skillRequest = requestRepository.findById(requestId).orElse(null);
        if (skillRequest == null) return ResponseEntity.notFound().build();
        skillRequest.setStatus("COMPLETED");
        requestRepository.save(skillRequest);
        badgeService.checkAndAwardBadges(skillRequest.getUser());
        return ResponseEntity.ok(skillRequest);
    }
}
