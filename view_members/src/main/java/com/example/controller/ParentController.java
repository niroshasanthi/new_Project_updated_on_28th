package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Child;
import com.example.model.Parent;
import com.example.model.Restriction;
import com.example.repository.ChildRepository;
import com.example.repository.ParentRepository;
import com.example.repository.RestrictionRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ParentController {

    @Autowired
    private ParentRepository parentRepo;

    @Autowired
    private ChildRepository childRepo;

    @Autowired
    private RestrictionRepository restrictionRepo;

    // Parent login
    @PostMapping("/login")
    public Parent login(@RequestBody Map<String, String> creds) {
        try {
            return parentRepo.findByUsernameAndPassword(
                creds.get("username").trim(),
                creds.get("password").trim()
            );
        } catch (Exception e) {
            e.printStackTrace(); // logs error in console
            return null;
        }
    }

    // Get children for a parent
    @GetMapping("/children/{parentId}")
    public List<Child> getChildren(@PathVariable Long parentId) {
        return childRepo.findByParentId(parentId);
    }

    // Get restriction for a child
    @GetMapping("/restriction/{parentId}/{childId}")
    public List<Restriction> getChildRestriction(@PathVariable Long parentId, @PathVariable Long childId){
        return restrictionRepo.findByChildIdAndParentId(childId, parentId);
    }
}
