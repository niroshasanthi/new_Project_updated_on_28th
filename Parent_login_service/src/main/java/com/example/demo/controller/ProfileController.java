package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.JwtService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Profile_Controller", description = "APIs for parent authentication and login")
@RestController
@RequestMapping("/api/parents")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final JwtService jwtService;

    public ProfileController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                logger.info("Cookie found: {}={}", cookie.getName(), cookie.getValue());
                if ("authToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    logger.info("authToken cookie extracted");
                    break;
                }
            }
        } else {
            logger.info("No cookies present in the request");
        }

        if (token == null || token.isEmpty()) {
            logger.warn("Unauthorized access attempt: No token found");
            return ResponseEntity.status(401).body("Unauthorized: No token found");
        }

        try {
            logger.info("Attempting to extract username from token");
            String email = jwtService.extractUsername(token);
            logger.info("Username extracted from token: {}", email);

            Map<String, String> profile = new HashMap<>();
            profile.put("email", email);

            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            logger.error("Error extracting username from token", e);
            return ResponseEntity.status(401).body("Unauthorized: Invalid token");
        }
    }
}
