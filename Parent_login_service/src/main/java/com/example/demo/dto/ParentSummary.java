package com.example.demo.dto;

public class ParentSummary {
private Long id;
private String name;
private String email;

public ParentSummary(Long id, String name, String email) {
this.id = id; this.name = name; this.email = email;
}

// getters
public Long getId() { return id; }
public String getName() { return name; }
public String getEmail() { return email; }
}