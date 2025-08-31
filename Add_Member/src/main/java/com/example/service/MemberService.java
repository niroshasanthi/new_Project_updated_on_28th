package com.example.service;

import com.example.model.ChildMember;
import com.example.model.Parent;
import com.example.repository.ChildMemberRepository;
import com.example.repository.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private ChildMemberRepository childMemberRepository;

    @Autowired
    private ParentRepository parentRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ChildMember addChildMember(String parentEmail, String username, String password) {
        String normalizedEmail = parentEmail.trim().toLowerCase();
        Parent parent = parentRepository.findByEmailIgnoreCase(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("Parent not found"));

        ChildMember child = new ChildMember();
        child.setUsername(username);
        child.setPassword(passwordEncoder.encode(password));  // encode password
        child.setParent(parent);
        return childMemberRepository.save(child);
    }


    public List<ChildMember> getChildrenByParentEmail(String parentEmail) {
        Parent parent = parentRepository.findByEmailIgnoreCase(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        return childMemberRepository.findByParent(parent);
    }

    public ChildMember loginChild(String username, String password) {
        ChildMember child = childMemberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        if (!new BCryptPasswordEncoder().matches(password, child.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return child;
    }
}
