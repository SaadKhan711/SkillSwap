package com.skills.swap.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SkillOffering> offerings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SkillRequest> requests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserSkill> userSkills;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value="user-badge")
    private Set<UserBadge> userBadges;


    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Set<SkillOffering> getOfferings() { return offerings; }
    public void setOfferings(Set<SkillOffering> offerings) { this.offerings = offerings; }
    public Set<SkillRequest> getRequests() { return requests; }
    public void setRequests(Set<SkillRequest> requests) { this.requests = requests; }
    public Set<UserSkill> getUserSkills() { return userSkills; }
    public void setUserSkills(Set<UserSkill> userSkills) { this.userSkills = userSkills; }
    
    // This is the missing method
    public Set<UserBadge> getUserBadges() { return userBadges; }
    public void setUserBadges(Set<UserBadge> userBadges) { this.userBadges = userBadges; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
