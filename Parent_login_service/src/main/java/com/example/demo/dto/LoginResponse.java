package com.example.demo.dto;

public class LoginResponse {
private String token;
private ParentSummary parent;

public LoginResponse(String token, ParentSummary parent) {
this.token = token; this.parent = parent;
}

public String getToken() { return token; }
public ParentSummary getParent() { return parent; }
}