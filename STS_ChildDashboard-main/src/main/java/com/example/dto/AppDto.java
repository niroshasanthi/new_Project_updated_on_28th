package com.example.dto;

public class AppDto {
    private Long id;
    private String name;
    private String category;

    // 🔹 Default constructor
    public AppDto() {}

    // 🔹 All-args constructor
    public AppDto(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // 🔹 Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
