package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.ChildMember;
import com.example.entity.Parent;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildMemberRepository extends JpaRepository<ChildMember, Long> {
	// Find all children belonging to a particular parent entity
	
	  Optional<ChildMember> findByUsername(String username);
	    List<ChildMember> findByParent(Parent parent);
	    List<ChildMember> findByParentId(Long parentId);
}
