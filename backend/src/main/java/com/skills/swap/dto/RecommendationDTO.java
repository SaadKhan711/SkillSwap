package com.skills.swap.dto;

import com.skills.swap.model.SkillOffering;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// We are removing @AllArgsConstructor and adding the constructor manually
public class RecommendationDTO {

    private SkillOffering offering;
    private double score;

    // Add this constructor manually
    public RecommendationDTO(SkillOffering offering, double score) {
        this.offering = offering;
        this.score = score;
    }

	public SkillOffering getOffering() {
		return offering;
	}

	public void setOffering(SkillOffering offering) {
		this.offering = offering;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}