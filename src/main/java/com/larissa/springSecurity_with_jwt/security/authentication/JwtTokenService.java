package com.larissa.springSecurity_with_jwt.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.larissa.springSecurity_with_jwt.security.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    public String generateToken(UserDetailsImpl user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer("auth0")
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expirationDate())
                    .withSubject(user.getUsername())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new JWTCreationException("Error generating token.", e);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid or expired token.", e);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("UTC")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("UTC")).plusHours(4).toInstant();
    }
}
