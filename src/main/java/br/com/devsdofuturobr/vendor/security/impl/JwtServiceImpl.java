package br.com.devsdofuturobr.vendor.security.impl;

import br.com.devsdofuturobr.vendor.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Override
    public boolean isExpiredToken(String token) {
        Jwt jwt = isTokenValid(token);
        Instant expiresAt = Objects.requireNonNull(jwt.getExpiresAt());
        return Instant.now().isAfter(expiresAt);
    }

    @Override
    public Jwt isTokenValid(String token) {
        try {
            return decoder.decode(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("Token is invalid");
        }
    }

    @Override
    public String generateToken(Authentication authentication) {
        System.out.println(authentication);
        Instant now = Instant.now();
        long expires = 3600L;
        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expires))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
