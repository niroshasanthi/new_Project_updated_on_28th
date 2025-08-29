package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findByName(String name);
}
