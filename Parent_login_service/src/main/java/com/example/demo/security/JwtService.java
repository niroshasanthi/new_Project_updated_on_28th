package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
private final Key key;
private final long expirationMillis;

public JwtService(
@Value("${security.jwt.secret}") String secret,
@Value("${security.jwt.expiration-minutes}") long expirationMinutes
) {
this.key = Keys.hmacShaKeyFor(secret.getBytes());
this.expirationMillis = expirationMinutes * 60 * 1000L;
}

public String generateToken(String subject, Map<String, Object> claims) {
Instant now = Instant.now();
return Jwts.builder()
.setClaims(claims)
.setSubject(subject)
.setIssuedAt(Date.from(now))
.setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
.signWith(key, SignatureAlgorithm.HS256)
.compact();
}

public Jws<Claims> parse(String token) {
return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
}
}




