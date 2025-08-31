package com.example.demo.dto;

import jakarta.validation.constraints.*;

public class LoginRequest {
    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
