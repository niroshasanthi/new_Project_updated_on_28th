package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

private final JwtService jwtService;
private final UserDetailsService userDetailsService;

public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
this.jwtService = jwtService;
this.userDetailsService = userDetailsService;
}

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
throws ServletException, IOException {
String authHeader = request.getHeader("Authorization");
if (authHeader != null && authHeader.startsWith("Bearer ")) {
String token = authHeader.substring(7);
try {
String email = jwtService.parse(token).getBody().getSubject();
UserDetails user = userDetailsService.loadUserByUsername(email);
var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
SecurityContextHolder.getContext().setAuthentication(auth);
} catch (Exception ignored) { /* invalid token → continue without auth */ }
}
filterChain.doFilter(request, response);
}
}