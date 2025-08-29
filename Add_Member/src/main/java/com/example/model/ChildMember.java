package com.example.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "child_members")
public class ChildMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // child login username

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    public ChildMember() {}

    public ChildMember(Parent parent, String username, String password) {
        this.parent = parent;
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = new BCryptPasswordEncoder().encode(password); }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }
}
