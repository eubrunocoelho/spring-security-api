package com.eubrunocoelho.spring_security_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtility {

    /**
     * Hash Generator: https://www.akto.io/tools/hmac-sha-256-hash-generator
     * <p>
     * Text: "Happy World 2024"
     * Key: "Key2024"
     * Algorithm: SHA-256 Hash (in Hex)
     */
    @Value("${com.eubrunocoelho.jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(Map<String, String> extraClaims, String userName, long expireInterval) {
        return Jwts
                .builder()
                .claims().add(extraClaims)
                .and()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireInterval))
                .signWith(getSignInKey())
                .compact();
    }

    public String getUserName(String token) {
        Claims claims = extractAllClaims(token);

        return claims.getSubject();
    }

    public boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);

        return claims.getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        // Extract claims after signature verification

        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
