package com.example.repository;

import com.example.model.ChildMember;
import com.example.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildMemberRepository extends JpaRepository<ChildMember, Long> {
    List<ChildMember> findByParent(Parent parent);
    Optional<ChildMember> findByUsername(String username); // for login
}
