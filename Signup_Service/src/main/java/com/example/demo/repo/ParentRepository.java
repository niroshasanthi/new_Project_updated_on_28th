package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByEmail(String email);
}
