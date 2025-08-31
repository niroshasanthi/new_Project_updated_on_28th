package com.example.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

    String token = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            logger.info("Cookie found: {}={}", cookie.getName(), cookie.getValue());
            if ("authToken".equals(cookie.getName())) {
                token = cookie.getValue();
                logger.info("JWT token found in cookie");
                break;
            }
        }
    } else {
        logger.info("No cookies found in request");
    }

    if (token == null) {
        String authHeader = request.getHeader("Authorization");
        logger.info("Authorization header: {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            logger.info("JWT token extracted from Authorization header");
        } else {
            logger.info("No JWT token present in Authorization header");
        }
    }

    if (token != null) {
        try {
            logger.info("Parsing JWT token");
            Jws<Claims> claimsJws = jwtService.parse(token);
            String email = claimsJws.getBody().getSubject();
            logger.info("JWT token valid. Subject (email): {}", email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                boolean valid = jwtService.validateToken(token, userDetails);
                logger.info("Token valid against UserDetails: {}", valid);

                if (valid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Security context updated with authentication for user: {}", email);
                } else {
                    logger.warn("Token validation failed for user: {}", email);
                }
            }
        } catch (Exception e) {
            logger.error("Exception during JWT processing: ", e);
        }
    } else {
        logger.warn("No JWT token found in request");
    }

    logger.info("Security Context Authentication: {}", SecurityContextHolder.getContext().getAuthentication());

    filterChain.doFilter(request, response);
}

}
