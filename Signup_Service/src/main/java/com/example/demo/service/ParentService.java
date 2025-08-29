package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ParentDto;
import com.example.demo.exception.ResourceAlreadyExistsException;
import com.example.demo.model.Parent;
import com.example.demo.repo.ParentRepository;



@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Parent signup(ParentDto parentDto) {
        if (parentRepository.findByEmail(parentDto.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("Parent with this email already exists.");
        }

        Parent parent = new Parent();
        parent.setName(parentDto.getName());
        parent.setEmail(parentDto.getEmail());
        parent.setPassword(passwordEncoder.encode(parentDto.getPassword())); // Hash password
        

        return parentRepository.save(parent);
    }
}