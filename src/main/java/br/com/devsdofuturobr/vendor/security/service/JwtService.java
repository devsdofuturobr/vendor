package br.com.devsdofuturobr.vendor.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {

    boolean isExpiredToken(String token);

    Jwt isTokenValid(String token);

    String generateToken(Authentication authentication);
}
