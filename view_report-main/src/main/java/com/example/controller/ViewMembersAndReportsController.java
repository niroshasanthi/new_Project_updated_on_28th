package com.example.controller;

import com.example.model.ChildMember;
import com.example.model.DailyUsageReport;
import com.example.model.Parent;
import com.example.repository.ChildMemberRepository;
import com.example.repository.DailyUsageReportRepository;
import com.example.repository.ParentRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "ReportViewing", description = "APIs for viewing usage and activity reports")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ViewMembersAndReportsController {

    private final ChildMemberRepository childMemberRepo;
    private final DailyUsageReportRepository reportRepo;
    private final ParentRepository parentRepo;

    @Autowired
    public ViewMembersAndReportsController(
            ChildMemberRepository childMemberRepo,
            DailyUsageReportRepository reportRepo,
            ParentRepository parentRepo) {
        this.childMemberRepo = childMemberRepo;
        this.reportRepo = reportRepo;
        this.parentRepo = parentRepo;
    }

    // 1. View all children for authenticated parent
    @Operation(summary = "View all children for authenticated parent")
    @GetMapping("/members")
    public List<ChildMember> viewMembers(Authentication authentication) {
        String parentEmail = authentication.getName();
        Parent parent = parentRepo.findByEmailIgnoreCase(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found for email: " + parentEmail));
        return childMemberRepo.findByParent(parent);
    }

    // 2. View a report for a member (child) on a specific date
    @Operation(summary = "Get daily usage reports for a child")
    @GetMapping("/reports/{childId}")
    public List<DailyUsageReport> viewReportForChildAndDate(
        @PathVariable Long childId,
        @RequestParam("date") String dateStr,
        Authentication authentication) {

        String parentEmail = authentication.getName();
        Parent parent = parentRepo.findByEmailIgnoreCase(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found for email: " + parentEmail));

        LocalDate date = LocalDate.parse(dateStr);

        ChildMember child = childMemberRepo.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        System.out.println("DEBUG Parent ID: " + parent.getId() + ", Child Parent ID: " + child.getParent().getId());

        // Temporarily comment out for debugging
        if (!child.getParent().getId().equals(parent.getId())) {
             throw new RuntimeException("Access denied: child does not belong to this parent.");
         }

        return reportRepo.findByParentidAndChildidAndReportDate(parent, child, date);
    }


}
