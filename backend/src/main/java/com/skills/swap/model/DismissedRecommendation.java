package com.skills.swap.model;

import jakarta.persistence.*;

@Entity
public class DismissedRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // Using LAZY fetch is a good practice for performance
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "skill_offering_id", nullable = false)
    private SkillOffering skillOffering;

    // --- Manual Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SkillOffering getSkillOffering() {
        return skillOffering;
    }

    public void setSkillOffering(SkillOffering skillOffering) {
        this.skillOffering = skillOffering;
    }
}
