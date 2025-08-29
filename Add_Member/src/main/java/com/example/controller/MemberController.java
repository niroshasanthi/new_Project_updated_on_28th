package com.example.controller;

import com.example.model.ChildMember;
import com.example.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(
  origins = {
    "http://localhost:3000",
    "http://localhost:3001",
    "http://localhost:3002",
    "http://localhost:3003",
    "http://localhost:3004",
    "http://localhost:3005",
    "http://localhost:3006",
    "http://localhost:3007",
    "http://localhost:3008",
    "http://localhost:3009"
  },
  allowCredentials = "true"
)
public class MemberController {

    @Autowired
    private MemberService memberService;

    // Add child with validation and error handling
    @PostMapping("/add")
    public ResponseEntity<?> addChild(@RequestBody Map<String, String> payload) {
        String parentIdStr = payload.get("parentId");
        if (parentIdStr == null || parentIdStr.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "parentId is required"));
        }

        Long parentId;
        try {
            parentId = Long.parseLong(parentIdStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid parentId format"));
        }

        String username = payload.get("username");
        String password = payload.get("password");

        try {
            ChildMember child = memberService.addChildMember(parentId, username, password);
            return ResponseEntity.ok(child);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to add child: " + e.getMessage()));
        }
    }

    // Get all children for a parent
    @GetMapping("/parent/{parentId}/children")
    public ResponseEntity<List<ChildMember>> getChildren(@PathVariable Long parentId) {
        List<ChildMember> children = memberService.getChildrenByParent(parentId);
        return ResponseEntity.ok(children);
    }

    // Child login
    @PostMapping("/child/login")
    public ResponseEntity<?> loginChild(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        try {
            ChildMember child = memberService.loginChild(username, password);
            // Return child + parent info
            return ResponseEntity.ok(Map.of(
                "childId", child.getId(),
                "childUsername", child.getUsername(),
                "parentId", child.getParent().getId(),
                "parentUsername", child.getParent().getUsername()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}
