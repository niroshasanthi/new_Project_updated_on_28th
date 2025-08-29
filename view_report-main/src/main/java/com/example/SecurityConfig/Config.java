package com.example.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Properly configure CORS
            .authorizeRequests(auth -> auth
                .requestMatchers("/api/login", "/api/children/**", "/api/restriction/**").permitAll() // Allow these routes for all
                .anyRequest().authenticated() // Secure the rest
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(httpBasic -> {});
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(
        	    "http://localhost:3000",
        	    "http://localhost:3001",
        	    "http://localhost:3002",
        	    "http://localhost:3003",
        	    "http://localhost:3004",
        	    "http://localhost:3005",
        	    "http://localhost:3006",
        	    "http://localhost:3007",
        	    "http://localhost:3008",
        	    "http://localhost:3009"
        	));

        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS globally to all paths
        return new CorsFilter(source);
    }

    // Correctly define CorsConfigurationSource
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // CORS settings for specific request patterns
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        // Apply CORS configuration to specific URL patterns
        source.registerCorsConfiguration("/api/login", config); // Apply CORS to /api/login
        source.registerCorsConfiguration("/api/children/**", config); // Apply CORS to /api/children/**
        source.registerCorsConfiguration("/api/restriction/**", config); // Apply CORS to /api/restriction/**

        return source;
    }
}
