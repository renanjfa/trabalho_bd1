package com.featurestore.featurestore.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {
    //senha de 32 bytes se nao llega ate o 32 recheia os espaco ate 32 bytes
    private static final String SECRET = "featurestore2026";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(
        String.format("%-32s", SECRET).getBytes()  // rellena con espacios hasta 32
    );

    // Token 24 horas
    private static final long EXPIRATION = 24 * 60 * 60 * 1000;

    // gera token a partir email
    public static String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public static boolean isValidToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}