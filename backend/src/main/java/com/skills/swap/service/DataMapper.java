package com.skills.swap.service;

import com.skills.swap.dto.*;
import com.skills.swap.model.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataMapper {

    // --- Methods for the User Profile ---

    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        if (user.getUserSkills() != null) {
            dto.setUserSkills(user.getUserSkills().stream()
                    .map(this::toUserSkillDTO)
                    .sorted(Comparator.comparing(UserSkillDTO::getSkillName))
                    .collect(Collectors.toList()));
        } else {
            dto.setUserSkills(Collections.emptyList());
        }

        if (user.getUserBadges() != null) {
            dto.setUserBadges(user.getUserBadges().stream()
                    .map(this::toBadgeDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setUserBadges(Collections.emptyList());
        }

        return dto;
    }

    public UserSkillDTO toUserSkillDTO(UserSkill userSkill) {
        UserSkillDTO dto = new UserSkillDTO();
        dto.setUserSkillId(userSkill.getId()); // Pass the ID for the delete button
        dto.setSkillId(userSkill.getSkill().getId()); // Pass the ID for the offer dropdown
        dto.setSkillName(userSkill.getSkill().getName()); 
        dto.setProficiency(userSkill.getProficiency());
        return dto;
    }

    public BadgeDTO toBadgeDTO(UserBadge userBadge) {
        BadgeDTO dto = new BadgeDTO();
        Badge badge = userBadge.getBadge();
        dto.setName(badge.getName());
        dto.setDescription(badge.getDescription());
        dto.setIcon(badge.getIcon());
        return dto;
    }


    // --- Methods for Marketplace ---

    public SkillOfferingDTO toSkillOfferingDTO(SkillOffering offering) {
        SkillOfferingDTO dto = new SkillOfferingDTO();
        dto.setId(offering.getId());
        dto.setUsername(offering.getUser().getUsername());
        dto.setSkillName(offering.getSkill().getName());
        return dto;
    }

    public SkillRequestDTO toSkillRequestDTO(SkillRequest request) {
        SkillRequestDTO dto = new SkillRequestDTO();
        dto.setId(request.getId());
        dto.setUserId(request.getUser().getId());
        dto.setUsername(request.getUser().getUsername());
        dto.setSkillName(request.getSkill().getName());
        dto.setStatus(request.getStatus());
        return dto;
    }
}
