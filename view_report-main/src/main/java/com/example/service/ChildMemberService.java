package com.example.service;

import com.example.model.ChildMember;
import com.example.model.Parent;
import com.example.repository.ChildMemberRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChildMemberService {

    @Autowired
    private ChildMemberRepository childMemberRepo;

    @Autowired
    private MockReportScheduler mockReportScheduler;

    public ChildMember addChildMember(ChildMember child) {
        return childMemberRepo.save(child);
    }

    public Optional<ChildMember> getChildMemberById(Long id) {
        return childMemberRepo.findById(id);
    }

    public List<ChildMember> getAllChildren() {
        return childMemberRepo.findAll();
    }

    public void deleteChildMember(Long id) {
        childMemberRepo.deleteById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ChildMember createChildMember(Parent parent, String username, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        ChildMember child = new ChildMember(parent, username, encodedPassword);
        return childMemberRepo.save(child);
    }

    public ChildMember updatePassword(Long childId, String rawPassword) {
        ChildMember child = childMemberRepo.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        child.setPassword(passwordEncoder.encode(rawPassword));
        return childMemberRepo.save(child);
    }
}
