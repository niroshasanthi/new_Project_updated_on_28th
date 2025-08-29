package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Parent;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
Optional<Parent> findByEmail(String email);
}