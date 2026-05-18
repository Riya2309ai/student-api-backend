package com.riya.studentapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "myverystrongsecretkeymyverystrongsecretkeymyverystrongsecretkeymyverystrongsecretkey12345";

    //without role getting token generate
//    public String generateToken(String username) {
//
//        return Jwts.builder()
//
//                .setSubject(username)
//
//                .setIssuedAt(new Date())
//
//                .setExpiration(
//                        new Date(
//                                System.currentTimeMillis()
//                                        + 1000 * 60 * 60
//                        )
//                )
//
//                .signWith(
//                        SignatureAlgorithm.HS256,
//                        SECRET_KEY
//                )
//
//                .compact();
//    }

    //with role getting token generate
    public String generateToken(
            String username,
            String role) {

        return Jwts.builder()

                .setSubject(username)

                .claim("role", role)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY
                )

                .compact();
    }


    public String extractUsername(String token) {

        Claims claims = Jwts.parser()

                .setSigningKey(SECRET_KEY)

                .parseClaimsJws(token)

                .getBody();

        return claims.getSubject();
    }

    public String extractRole(
            String token) {

        Claims claims = Jwts.parser()

                .setSigningKey(SECRET_KEY)

                .parseClaimsJws(token)

                .getBody();

        return claims.get("role", String.class);
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}