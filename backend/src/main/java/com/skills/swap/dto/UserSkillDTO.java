package com.skills.swap.dto;

public class UserSkillDTO {
    private Long userSkillId; // The ID of the UserSkill link itself (for deleting)
    private Long skillId;     // The ID of the actual Skill (for offering)
    private String skillName;
    private String proficiency;

    // Getters and Setters
    public Long getUserSkillId() { return userSkillId; }
    public void setUserSkillId(Long userSkillId) { this.userSkillId = userSkillId; }
    public Long getSkillId() { return skillId; }
    public void setSkillId(Long skillId) { this.skillId = skillId; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getProficiency() { return proficiency; }
    public void setProficiency(String proficiency) { this.proficiency = proficiency; }
}
