package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ChildMember;
import com.example.model.Parent;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildMemberRepository extends JpaRepository<ChildMember, Long> {
	// Find all children belonging to a particular parent entity
    List<ChildMember> findByParent(Parent parent);

    // Alternative: Find all children by parent ID
    List<ChildMember> findByParentId(Long parentId);

    // Find a child member by their unique username
    Optional<ChildMember> findByUsername(String username);
}
