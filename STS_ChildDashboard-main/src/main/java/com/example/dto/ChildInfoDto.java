package com.example.dto;

public class ChildInfoDto {
    private Long id;
    private String username;
    private Long parentId;
    public ChildInfoDto() {}
    public ChildInfoDto(Long id, String username, Long parentId) {
        this.id = id; this.username = username; this.parentId = parentId;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
}
