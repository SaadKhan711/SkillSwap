package com.skills.swap.service;

import com.skills.swap.model.*;
import com.skills.swap.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BadgeService {

    @Autowired private BadgeRepository badgeRepository;
    @Autowired private UserBadgeRepository userBadgeRepository;
    @Autowired private UserSkillRepository userSkillRepository;
    @Autowired private SkillOfferingRepository skillOfferingRepository;
    @Autowired private SkillRequestRepository skillRequestRepository;

    /**
     * This is the main method that checks all badge conditions for a given user.
     */
    @Transactional
    public void checkAndAwardBadges(User user) {
        checkFirstStepsBadge(user);
        checkMentorBadge(user);
//        checkPolymathBadge(user);
        checkCommunityBuilderBadge(user);
    }

    // --- Private check methods for each badge ---

    private void checkFirstStepsBadge(User user) {
        long skillCount = userSkillRepository.countByUser(user);
        if (skillCount >= 1) {
            awardBadge(user, "First Steps");
        }
    }

    private void checkMentorBadge(User user) {
        long offeringCount = skillOfferingRepository.countByUser(user);
        if (offeringCount >= 3) {
            awardBadge(user, "Mentor");
        }
    }

    // *** UPDATED LOGIC FOR POLYMATH BADGE ***
  //  private void checkPolymathBadge(User user) {
//        // 1. Get all skills for the user.
//        // 2. For each skill, get its tags.
//        // 3. Take only the FIRST tag as the "primary category".
//        // 4. Collect these primary categories into a Set to count the unique ones.
//        Set<String> distinctPrimaryCategories = userSkillRepository.findByUser(user).stream()
//                .map(userSkill -> userSkill.getSkill().getTags())
//                .filter(tags -> tags != null && !tags.isEmpty())
//                .map(tags -> tags.split(",")[0].trim()) // Take only the first tag
//                .collect(Collectors.toSet());
//        
//        // 5. Award the badge only if the user has skills in 3 or more unique primary categories.
//        if (distinctPrimaryCategories.size() >= 3) {
//            awardBadge(user, "Polymath");
//        }
//    }

    private void checkCommunityBuilderBadge(User user) {
        long completedRequests = skillRequestRepository.countByUserAndStatus(user, "COMPLETED");
        if (completedRequests >= 5) {
            awardBadge(user, "Community Builder");
        }
    }

    /**
     * A helper method to award a badge if the user doesn't already have it.
     */
    private void awardBadge(User user, String badgeName) {
        badgeRepository.findByName(badgeName).ifPresent(badge -> {
            if (!userBadgeRepository.existsByUserAndBadge(user, badge)) {
                UserBadge userBadge = new UserBadge();
                userBadge.setUser(user);
                userBadge.setBadge(badge);
                userBadgeRepository.save(userBadge);
                System.out.println("Awarded badge '" + badgeName + "' to user '" + user.getUsername() + "'");
            }
        });
    }

    /**
     * Creates the master list of badges in the database if they don't exist.
     */
    @Transactional
    public void initializeBadges() {
        createBadgeIfNotFound("First Steps", "Added your first skill to your profile.", "footprint");
        createBadgeIfNotFound("Mentor", "Offered 3 or more different skills.", "school");
        createBadgeIfNotFound("Polymath", "Mastered skills in 3+ different categories.", "local_library");
        createBadgeIfNotFound("Community Builder", "Completed 5 or more skill requests.", "groups");
    }

    private void createBadgeIfNotFound(String name, String description, String icon) {
        if (badgeRepository.findByName(name).isEmpty()) {
            Badge badge = new Badge();
            badge.setName(name);
            badge.setDescription(description);
            badge.setIcon(icon);
            badgeRepository.save(badge);
        }
    }
}
