package com.example.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key secretKey;
    private final long expirationMillis;

    public JwtService(
        @Value("${security.jwt.secret}") String secret,
        @Value("${security.jwt.expiration-minutes}") long expirationMinutes
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expirationMinutes * 60 * 1000L;
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirationMillis))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token);
    }
    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = parse(token).getBody().getSubject();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return parse(token).getBody().getExpiration().before(new Date());
    }
}
