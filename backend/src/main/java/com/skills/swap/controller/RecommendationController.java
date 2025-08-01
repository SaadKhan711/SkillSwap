package com.skills.swap.controller;

import com.skills.swap.dto.LinkRequest;
import com.skills.swap.dto.RecommendationDTO;
import com.skills.swap.model.*;
import com.skills.swap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired private SkillRequestRepository requestRepository;
    @Autowired private SkillOfferingRepository offeringRepository;
    @Autowired private DismissedRecommendationRepository dismissedRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/hybrid/{userId}")
    public List<RecommendationDTO> hybridRecommend(@PathVariable Long userId) {
        List<RecommendationDTO> collaborativeRecs = getCollaborativeRecommendations(userId);
        if (!collaborativeRecs.isEmpty()) {
            System.out.println("LOG: Found " + collaborativeRecs.size() + " collaborative recommendations.");
            return collaborativeRecs;
        }
        System.out.println("LOG: No collaborative recommendations found. Falling back to content-based.");
        return getContentBasedRecommendations(userId);
    }

    private List<RecommendationDTO> getCollaborativeRecommendations(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return List.of();

        Set<Skill> userCompletedSkills = requestRepository.findByUserIdAndStatus(userId, "COMPLETED").stream().map(SkillRequest::getSkill).collect(Collectors.toSet());
        if (userCompletedSkills.isEmpty()) return List.of();

        List<User> peers = requestRepository.findAllWithDetails().stream()
                .filter(req -> "COMPLETED".equals(req.getStatus()) && !req.getUser().getId().equals(userId) && userCompletedSkills.contains(req.getSkill()))
                .map(SkillRequest::getUser).distinct().toList();
        if (peers.isEmpty()) return List.of();

        Map<Skill, List<User>> skillToPeersMap = new HashMap<>();
        for (User peer : peers) {
            List<SkillRequest> peerCompletedRequests = requestRepository.findByUserIdAndStatus(peer.getId(), "COMPLETED");
            for (SkillRequest peerRequest : peerCompletedRequests) {
                Skill skill = peerRequest.getSkill();
                if (!userCompletedSkills.contains(skill)) {
                    skillToPeersMap.computeIfAbsent(skill, k -> new ArrayList<>()).add(peer);
                }
            }
        }

        List<SkillOffering> potentialOfferings = offeringRepository.findAllWithDetails().stream()
                .filter(offering -> skillToPeersMap.containsKey(offering.getSkill())).toList();

        List<Long> dismissedOfferingIds = dismissedRepository.findByUser(user).stream().map(d -> d.getSkillOffering().getId()).toList();
        List<SkillOffering> finalOfferings = potentialOfferings.stream().filter(offering -> !dismissedOfferingIds.contains(offering.getId())).toList();
        
        return finalOfferings.stream()
                .map(offering -> new RecommendationDTO(offering, skillToPeersMap.getOrDefault(offering.getSkill(), Collections.emptyList()).size()))
                .sorted(Comparator.comparing(RecommendationDTO::getScore).reversed()).toList();
    }

    private List<RecommendationDTO> getContentBasedRecommendations(Long userId) {
        // This is a simplified version as we removed tags. A more robust version would be needed for a real app.
        return List.of();
    }

    @PostMapping("/dismiss")
    public ResponseEntity<Void> dismissRecommendation(@RequestBody LinkRequest dismissRequest) {
        User user = userRepository.findById(dismissRequest.getUserId()).orElse(null);
        SkillOffering offering = offeringRepository.findById(dismissRequest.getSkillId()).orElse(null);
        if (user == null || offering == null) return ResponseEntity.badRequest().build();
        DismissedRecommendation dismissed = new DismissedRecommendation();
        dismissed.setUser(user);
        dismissed.setSkillOffering(offering);
        dismissedRepository.save(dismissed);
        return ResponseEntity.ok().build();
    }
}