package com.example.controller;

import com.example.dto.AppDto;
import com.example.dto.RestrictionDto;
import com.example.entity.ChildMember;
import com.example.repository.ChildMemberRepository;
import com.example.service.ChildDashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "ChildDashboard", description = "APIs related to child's dashboard and usage details")
@RestController
@RequestMapping("/api/child-dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class ChildDashboardController {

    @Autowired
    private ChildDashboardService childDashboardService;

    @Autowired
    private ChildMemberRepository childMemberRepository;

    @Operation(summary = "Get dashboard data for a child")
    @GetMapping("/dashboard/apps")
    public List<AppDto> getDashboardApps(@RequestParam String username) {
        ChildMember child = childMemberRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Child not found"));
        return childDashboardService.getDashboardApps(child.getId());
    }

    @Operation(summary = "Get restrictions data for a child")
    @GetMapping("/restrictions")
    public List<RestrictionDto> getRestrictions(@RequestParam String username) {
        ChildMember child = childMemberRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Child not found"));
        return childDashboardService.getRestrictionsByChildId(child.getId());
    }
}
