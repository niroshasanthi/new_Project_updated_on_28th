package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.demo.model.Child;
import com.example.demo.repo.ChildRepository;

@Service
public class ChildUserDetailsService implements UserDetailsService {

    @Autowired
    private ChildRepository childRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Child child = childRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Child not found with name: " + name));

        return User.withUsername(child.getName())
                .password(child.getPassword()) // already BCrypt encoded
                .roles("CHILD")
                .build();
    }
}
