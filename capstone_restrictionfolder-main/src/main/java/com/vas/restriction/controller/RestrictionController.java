package com.vas.restriction.controller;

import com.vas.restriction.model.ChildMember;
import com.vas.restriction.model.Parent;
import com.vas.restriction.model.Restriction;
import com.vas.restriction.repository.ChildMemberRepository;
import com.vas.restriction.repository.ParentRepository;
import com.vas.restriction.service.RestrictionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Tag(name = "RestrictionManagement", description = "APIs to add and manage usage restrictions")
@RestController
@RequestMapping("/api/restrictions")
@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:3001", "http://localhost:3002", 
        "http://localhost:3003", "http://localhost:3004", "http://localhost:3005",
        "http://localhost:3006", "http://localhost:3007", "http://localhost:3008", 
        "http://localhost:3009"
    }, allowCredentials = "true")
public class RestrictionController {

    private static final Logger logger = LoggerFactory.getLogger(RestrictionController.class);

    private final RestrictionService restrictionService;
    private final ParentRepository parentRepository;
    private final ChildMemberRepository childMemberRepository;

    public RestrictionController(RestrictionService restrictionService,
                                 ParentRepository parentRepository,
                                 ChildMemberRepository childMemberRepository) {
        this.restrictionService = restrictionService;
        this.parentRepository = parentRepository;
        this.childMemberRepository = childMemberRepository;
    }

    @Operation(summary = "Add a restriction for a child")
    @PostMapping
    public Restriction addRestriction(@RequestBody Map<String, Object> requestBody, Authentication authentication) {
        String parentEmail = authentication.getName();

        Parent parent = parentRepository.findByEmailIgnoreCase(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found for email: " + parentEmail));

        Restriction restriction = new Restriction();

        // Set parent
        restriction.setParent(parent);

        // Extract childId from requestBody and fetch ChildMember entity
        final Long childId;
        if(requestBody.containsKey("childId")) {
            Object idObj = requestBody.get("childId");
            if (idObj instanceof Integer) {
                childId = ((Integer) idObj).longValue();
            } else if (idObj instanceof Long) {
                childId = (Long) idObj;
            } else if (idObj instanceof String) {
                childId = Long.parseLong((String) idObj);
            } else {
                throw new RuntimeException("Invalid childId type");
            }
        } else {
            throw new RuntimeException("childId is required");
        }


        ChildMember childMember = childMemberRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child with id " + childId + " not found"));

        restriction.setChildMember(childMember);

        // Set other fields from requestBody as needed, e.g. appName, dataLimit, etc.
        restriction.setAppName((String) requestBody.get("appName"));
        restriction.setDataLimit((String) requestBody.get("dataLimit"));
        restriction.setTimeLimit((String) requestBody.get("timeLimit"));
        restriction.setCustomRule((String) requestBody.get("customRule"));

        // Handle bedTimeStart and bedTimeEnd if sent as String, parse to LocalTime
        if(requestBody.get("bedTimeStart") != null) {
            restriction.setBedTimeStart(LocalTime.parse((String) requestBody.get("bedTimeStart")));
        }
        if(requestBody.get("bedTimeEnd") != null) {
            restriction.setBedTimeEnd(LocalTime.parse((String) requestBody.get("bedTimeEnd")));
        }

        // Save
        return restrictionService.addRestriction(restriction);
    }

    @Operation(summary = "Get restrictions for a child")
    @GetMapping("/my-children")
    public List<ChildMember> getChildrenForParent(Authentication authentication) {
        String parentEmail = authentication.getName();
        logger.info("Fetching children for parent email: {}", parentEmail);

        Parent parent = parentRepository.findByEmailIgnoreCase(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found for email: " + parentEmail));

        List<ChildMember> children = childMemberRepository.findByParentId(parent.getId());
        logger.info("Found {} children for parent id {}", children.size(), parent.getId());

        return children;
    }

}
