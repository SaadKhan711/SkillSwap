package com.skills.swap.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // e.g., "Mentor"

    @Column(nullable = false)
    private String description; // e.g., "Offered 3 or more different skills"

    @Column(nullable = false)
    private String icon; // e.g., "local_library" (Material Symbols icon name)

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(id, badge.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
