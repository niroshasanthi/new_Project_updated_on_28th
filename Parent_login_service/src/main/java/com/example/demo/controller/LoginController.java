package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginRequest;
import com.example.demo.security.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "ParentLogin", description = "APIs for parent authentication and login")
@RestController
@RequestMapping("/api/parents")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Operation(summary = "Authenticate parent and generate JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtService.generateToken(request.getEmail(), Map.of());

            // ✅ Make cookie valid for ALL localhost ports
            ResponseCookie cookie = ResponseCookie.from("authToken", token)
                .httpOnly(true)
                .secure(false) // change to true if using HTTPS
                .path("/")     // cookie valid for all paths
                .domain("localhost") // <--- important (works for localhost across ports)
                .maxAge(24 * 60 * 60) // 1 day
                .sameSite("Lax")
                .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            Map<String, Object> userInfo = Map.of(
                "email", request.getEmail(),
                "name", "Sundar Pichai",
                "roles", List.of("ROLE_PARENT")
            );

            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "user", userInfo
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}
