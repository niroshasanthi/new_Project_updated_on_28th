package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Restriction;

@Repository
public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
    Restriction findByChildId(Long childId);
}
