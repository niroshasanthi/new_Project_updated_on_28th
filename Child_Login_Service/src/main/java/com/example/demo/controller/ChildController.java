package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ChildLoginRequest;
import com.example.demo.security.ChildJwtUtil;


@RestController
@RequestMapping("/api/children")
public class ChildController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ChildJwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ChildLoginRequest request) {
        try {
            // Authenticate child
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
            );

            // Generate JWT
            String token = jwtUtil.generateToken(request.getName());

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid name or password");
        }
    }
}
