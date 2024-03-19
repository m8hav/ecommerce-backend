package org.project.ecommercebackend.service.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.function.Function;

public interface JWTService {
    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Key getSignKey();

    Claims extractAllClaims(String token);

    <T> T extractClaim(String Token, Function<Claims, T> claimsResolvers);

    String extractUserName(String token);
}
