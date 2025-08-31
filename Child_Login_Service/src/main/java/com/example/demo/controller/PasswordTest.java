package com.example.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "111111";
        String storedHash = "$2a$10$me3TAIg3wmyHIDKHohyzROsZvZLNZb54B.LW7F7GlEBkNTLlOOrvK"; 

        boolean matches = encoder.matches(rawPassword, storedHash);
        System.out.println("Password matches: " + matches);
    }
}
