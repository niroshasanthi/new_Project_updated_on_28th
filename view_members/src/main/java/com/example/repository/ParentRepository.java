package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByUsernameAndPassword(String username, String password);
}
