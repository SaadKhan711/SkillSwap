package com.skills.swap.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class UserBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value="user-badge")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Badge getBadge() { return badge; }
    public void setBadge(Badge badge) { this.badge = badge; }
}
