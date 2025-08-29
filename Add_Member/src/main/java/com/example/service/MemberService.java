package com.example.service;

import com.example.model.ChildMember;
import com.example.model.Parent;
import com.example.repository.ChildMemberRepository;
import com.example.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private ChildMemberRepository childMemberRepository;

    @Autowired
    private ParentRepository parentRepository;

    // Add child for a parent
    public ChildMember addChildMember(Long parentId, String childUsername, String childPassword) {
        Parent parent = parentRepository.findById(parentId)
                         .orElseThrow(() -> new RuntimeException("Parent not found"));
        ChildMember child = new ChildMember(parent, childUsername, childPassword);
        return childMemberRepository.save(child);
    }

    // Get all children for a parent
    public List<ChildMember> getChildrenByParent(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                         .orElseThrow(() -> new RuntimeException("Parent not found"));
        return childMemberRepository.findByParent(parent);
    }

    // Child login
    public ChildMember loginChild(String username, String password) {
        ChildMember child = childMemberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        if (!new BCryptPasswordEncoder().matches(password, child.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return child; // includes parent info
    }
}
