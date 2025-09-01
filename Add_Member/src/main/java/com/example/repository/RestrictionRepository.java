package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Restriction;

import java.util.List;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {
    List<Restriction> findByParentId(Long parentId);
    List<Restriction> findByChildMemberId(Long childMemberId);
}
