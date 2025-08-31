package com.example.controller;

import com.example.entity.ChildMember;
import com.example.repository.ChildMemberRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Tag(name = "Child_Login_Service", description = "APIs related to child authentication and login ")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private ChildMemberRepository childMemberRepository;
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<ChildMember> childOpt = childMemberRepository.findByUsername(request.getUsername());
        if (childOpt.isPresent()) {
            ChildMember child = childOpt.get();
            // Verify raw password matches hashed password in DB
            if (passwordEncoder.matches(request.getPassword(), child.getPassword())) {
                return ResponseEntity.ok(child.getUsername());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}
   

