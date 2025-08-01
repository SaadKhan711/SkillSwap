package com.skills.swap.dto;

public class SkillRequestDTO {
    private Long id;
    private Long userId;
    private String username;
    private String skillName;
    private String status;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
