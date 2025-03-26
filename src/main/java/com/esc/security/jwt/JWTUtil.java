package com.esc.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JWTUtil {
    public static final String SECRET_KEY = "spring-Boot-example-fullstack-token-secret-key";

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String issueToken(String subject, Map<String, Object> claims) {

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .setIssuer("Khaled_Mohammed_ESC >_<")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5, DAYS)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String issueToken(String subject) {
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String... scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, List<String> scopes) {
        return issueToken(subject, Map.of("scopes", scopes));
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        Date today = Date.from(Instant.now());
        return getClaims(token).getExpiration().before(today);
    }

    public boolean isTokenValid(String token, String userName) {
        String subject = getSubject(token);
        return subject.equals(userName) && !isTokenExpired(token);
    }
}

