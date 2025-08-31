package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ChildLoginRequest;
import com.example.demo.security.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Child_Login_Service", description = "APIs related to child Login")
@RestController
@RequestMapping("/api/children")
public class ChildController {

    private static final Logger logger = LoggerFactory.getLogger(ChildController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    // Use JwtService here instead of ChildJwtUtil
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ChildLoginRequest request, HttpServletResponse response) {
    	
    	 String username = request.getName();
    	    String password = request.getPassword();

        logger.info("Login attempt - username: {}, password: {}", request.getName(), request.getPassword()); // Remove password logging in production!

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())
            );

            // Generate token with username as subject, no extra claims here
            String token = jwtService.generateToken(username, Map.of());

            Cookie cookie = new Cookie("authToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // set true if using HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // 1 day in seconds

            response.addCookie(cookie);

            return ResponseEntity.ok().build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid name or password");
        }
    }
}
