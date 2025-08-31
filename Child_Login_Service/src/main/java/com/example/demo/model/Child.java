package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "child_members")
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;  // username to login with

    @Column(nullable = false)
    private String password;  // stored as bcrypt hash

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    public Child() {}

    public Child(Parent parent, String username, String password) {
        this.parent = parent;
        this.username = username;
        this.password = password;
    }

    // Getters / Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Parent getParent() { return parent; }
    public void setParent(Parent parent) { this.parent = parent; }
}
