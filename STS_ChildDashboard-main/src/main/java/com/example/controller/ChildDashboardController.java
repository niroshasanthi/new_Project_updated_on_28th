package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AppDto;
import com.example.service.ChildDashboardService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/child-dashboard")
public class ChildDashboardController {

    private final ChildDashboardService dashboardService;

    public ChildDashboardController(ChildDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{childId}/apps")
    public ResponseEntity<List<AppDto>> getChildApps(@PathVariable Long childId) {
        return ResponseEntity.ok(dashboardService.getChildApps(childId));
    }
}
