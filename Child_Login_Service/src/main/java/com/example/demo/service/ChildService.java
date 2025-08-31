package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.demo.model.Child;
import com.example.demo.model.Parent;
import com.example.demo.repo.ChildRepository;
import com.example.demo.repo.ParentRepository;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestTemplate restTemplate;

    private final String parentServiceUrl = "http://localhost:8081";

    public Child addChild(Long parentId, String username, String rawPassword) {
        Boolean exists = restTemplate.getForObject(parentServiceUrl + "/api/parents/" + parentId + "/exists", Boolean.class);
        if (exists == null || !exists) {
            throw new RuntimeException("Parent not found");
        }

        Parent parent = parentRepository.findById(parentId)
                       .orElseThrow(() -> new RuntimeException("Parent entity not found"));

        if (childRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        Child child = new Child();
        child.setUsername(username);
        child.setPassword(passwordEncoder.encode(rawPassword));  // encode password before saving
        child.setParent(parent);

        return childRepository.save(child);
    }
}
