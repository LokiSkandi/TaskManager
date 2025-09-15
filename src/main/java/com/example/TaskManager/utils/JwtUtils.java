package com.example.TaskManager.utils;

import com.example.TaskManager.dto.UserDetailsImpl;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.expiration.ms}")
    private long jwtExpirationMs;
    @Value("${jwt.secret}")
    private String jwtSecret;
    private static final SecureDigestAlgorithm<SecretKey, SecretKey> JWT_ALGORITHM = Jwts.SIG.HS256;

    private SecretKey getKey () {
       return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl  userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
                .signWith(getKey(), JWT_ALGORITHM)
                .compact();
    }
     public String getUsernameFromJwtToken(String token) {
         JwtParser jwtParser = Jwts.parser().verifyWith(getKey()).build();
         return jwtParser.parseSignedClaims(token).getPayload().getSubject();
     }
}
