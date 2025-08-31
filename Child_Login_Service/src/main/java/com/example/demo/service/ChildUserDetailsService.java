package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.example.demo.model.Child;
import com.example.demo.repo.ChildRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ChildUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ChildUserDetailsService.class);

    @Autowired
    private ChildRepository childRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Child child = childRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Child not found with username: " + username));

        
        logger.info("Loaded child username: {}", child.getUsername());
        logger.info("Stored password hash: {}", child.getPassword());

        return User.withUsername(child.getUsername())
                .password(child.getPassword()) // bcrypt hash
                .roles("CHILD")
                .build();
    }
}
