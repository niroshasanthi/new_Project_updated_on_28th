package com.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that authenticates requests by validating JWT token 
 * present either in the "authToken" cookie or Authorization header.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;

        // Try to get token from cookie 'authToken'
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("authToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // If token not found in cookie, try Authorization header
        if (token != null) {
        	   System.out.println("Auth token received: " + token);
        	   try {
        	      String email = jwtService.extractUsername(token);
        	      System.out.println("JWT subject: " + email);
        	      // Continue with validation...
        	   } catch (Exception ex) {
        	      System.err.println("JWT Exception: " + ex.getMessage());
        	   }
        	}


        // Validate and parse token
        if (token != null) {
            try {
                Jws<Claims> claimsJws = jwtService.parse(token);
                String email = claimsJws.getBody().getSubject();

                // If valid email and no existing authentication in context
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Load UserDetails for authenticated user
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    // Validate token integrity and expiration
                    if (jwtService.validateToken(token, userDetails)) {
                        // Create authentication token and set Security Context
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception ex) {
                // Invalid token or parsing error, ignore or log as needed
            }
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
