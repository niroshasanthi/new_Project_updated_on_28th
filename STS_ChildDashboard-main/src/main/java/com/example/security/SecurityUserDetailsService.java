package com.example.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Minimal UserDetailsService that trusts the email from JWT token without any DB lookup.
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Here, password is not needed since authentication is handled by JWT validation
        return User.withUsername(email)
                .password("") // empty password since JWT is trusted
                .roles("CHILD") // assign role PARENT or adjust as needed
                .build();
    }
}
