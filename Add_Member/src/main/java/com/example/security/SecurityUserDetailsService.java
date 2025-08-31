package com.example.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.Parent;
import com.example.repository.ParentRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final ParentRepository parentRepository;

    public SecurityUserDetailsService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String normalizedEmail = email.trim().toLowerCase();

        Parent parent = parentRepository.findByEmailIgnoreCase(normalizedEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // Map Parent to UserDetails
        return User.builder()
            .username(parent.getEmail())
            .password(parent.getPassword())
            .roles("PARENT") // put appropriate roles here
            .build();
    }
}
