package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Child;
import com.example.demo.repo.ChildRepository;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    private final String parentServiceUrl = "http://localhost:8081/api/parents"; // example

    public Child addChild(Long parentId, String name, String password) {
        // Optional: check if parent exists in Parent microservice
        Boolean parentExists = restTemplate.getForObject(parentServiceUrl + "/" + parentId + "/exists", Boolean.class);
        if (parentExists == null || !parentExists) {
            throw new RuntimeException("Parent not found in Parent Service");
        }

        if (childRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Child with this name already exists");
        }

        Child child = new Child();
        child.setName(name);
        child.setPassword(passwordEncoder.encode(password));
        child.setParentId(parentId);

        return childRepository.save(child);
    }
}
