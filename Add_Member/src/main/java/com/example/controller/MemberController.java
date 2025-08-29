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
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // Add child
    @PostMapping("/add")
    public ResponseEntity<?> addChild(@RequestBody Map<String, String> payload) {
        Long parentId = Long.parseLong(payload.get("parentId"));
        String username = payload.get("username");
        String password = payload.get("password");

        ChildMember child = memberService.addChildMember(parentId, username, password);
        return ResponseEntity.ok(child);
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
