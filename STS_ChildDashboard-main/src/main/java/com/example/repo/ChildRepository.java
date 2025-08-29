package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
}
