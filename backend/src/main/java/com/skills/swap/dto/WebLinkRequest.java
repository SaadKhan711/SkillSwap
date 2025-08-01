package com.skills.swap.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebLinkRequest {
    private Long userId;
    private Long skillId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getSkillId() {
		return skillId;
	}
	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}
}