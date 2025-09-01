package com.vas.restriction.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Return UserDetails with email as username, no password checking here
        // Password not needed since JWT already verified
        return User.withUsername(email)
                .password("") // No password needed here
                .roles("PARENT")
                .build();
    }
}


