package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 🔹 Main security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // uses corsFilter bean
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/child-dashboard/**").authenticated() // protect child dashboard
                .anyRequest().permitAll() // allow all other requests
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(); // enable Basic Auth for now

        return http.build();
    }

    // 🔹 In-memory user for testing
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var user = User.withUsername("parentUser")
            .password(passwordEncoder.encode("password"))
            .roles("PARENT")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    // 🔹 Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔹 CORS filter to allow React frontend
    @Bean
    public CorsFilter corsFilter() {
        var config = new CorsConfiguration();
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

// your React dev URL
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
