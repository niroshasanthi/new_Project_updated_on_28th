
package com.vas.restriction.controller;

import com.vas.restriction.model.Restriction;
import com.vas.restriction.service.RestrictionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="http://localhost:3000",allowCredentials="true")
@RestController
@RequestMapping("/api/restrictions")
public class RestrictionController {

    private final RestrictionService restrictionService;

    public RestrictionController(RestrictionService restrictionService) {
        this.restrictionService = restrictionService;
    }

    // Add new restriction for a member
    @PostMapping
    public Restriction addRestriction(@RequestBody Restriction restriction) {
        return restrictionService.addRestriction(restriction);
    }

    // Get restrictions for a member
    @GetMapping("/{memberId}")
    public List<Restriction> getRestrictions(@PathVariable Long memberId) {
        return restrictionService.getRestrictionsForMember(memberId);
    }
}
