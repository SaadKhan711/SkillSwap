package com.skills.swap.dto;

import java.util.List;

/**
 * This DTO represents the complete user profile view.
 */
public class UserProfileDTO {
    private Long id;
    private String username;
    private List<UserSkillDTO> userSkills;
    private List<BadgeDTO> userBadges;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<UserSkillDTO> getUserSkills() { return userSkills; }
    public void setUserSkills(List<UserSkillDTO> userSkills) { this.userSkills = userSkills; }
    public List<BadgeDTO> getUserBadges() { return userBadges; }
    public void setUserBadges(List<BadgeDTO> userBadges) { this.userBadges = userBadges; }
}