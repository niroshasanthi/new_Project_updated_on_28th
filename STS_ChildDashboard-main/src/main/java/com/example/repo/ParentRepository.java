package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
}
