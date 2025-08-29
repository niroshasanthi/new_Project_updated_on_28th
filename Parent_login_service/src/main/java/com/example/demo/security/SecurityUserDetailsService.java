package com.example.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Parent;
import com.example.demo.repo.ParentRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Parent parent = parentRepository.findByEmail(email)
        		.orElseThrow(() -> new UsernameNotFoundException("Parent not found with email: " + email));

        // Here we map Parent entity to Spring Security's User object
        return User.withUsername(parent.getEmail())
                .password(parent.getPassword()) // already encoded with BCrypt
                .roles("PARENT") // You can later extend roles if needed
                .build();
    }
}