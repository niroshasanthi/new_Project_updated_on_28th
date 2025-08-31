package com.example.controller;

import com.example.model.ChildMember;
import com.example.security.JwtAuthFilter;
import com.example.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "MemberManagement", description = "APIs for managing family members")
@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = {
    "http://localhost:3000", "http://localhost:3001", "http://localhost:3002",
    "http://localhost:3003", "http://localhost:3004", "http://localhost:3005",
    "http://localhost:3006", "http://localhost:3007", "http://localhost:3008",
    "http://localhost:3009"
}, allowCredentials = "true")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Operation(summary = "Add a new family member")
    @PostMapping("/add")
    public ResponseEntity<?> addChild(@RequestBody Map<String, String> payload) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized"));
        }
        
        String parentEmail = auth.getName().trim().toLowerCase();
        String username = payload.get("username");
        String password = payload.get("password");
        
        ChildMember child = memberService.addChildMember(parentEmail, username, password);
        return ResponseEntity.ok(child);
    }




    @GetMapping("/my-children")
    public ResponseEntity<?> getMyChildren() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }
        String parentEmail = auth.getName();
        List<ChildMember> children = memberService.getChildrenByParentEmail(parentEmail);
        return ResponseEntity.ok(children);
    }

    @PostMapping("/child/login")
    public ResponseEntity<?> loginChild(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");
        try {
            ChildMember child = memberService.loginChild(username, password);
            return ResponseEntity.ok(Map.of(
                    "childId", child.getId(),
                    "childUsername", child.getUsername(),
                    "parentId", child.getParent().getId(),
                    "parentEmail", child.getParent().getEmail()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}
