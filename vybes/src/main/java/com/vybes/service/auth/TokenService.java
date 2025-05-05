package com.vybes.service.auth;

import com.vybes.model.VybesUser;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Value("${app.jwt.issuer}")
    private String issuer;

    @Value("${app.jwt.access-token-expiration-minutes:15}")
    private int accessTokenExpirationMinutes;

    @Value("${app.jwt.refresh-token-expiration-days:14}")
    private int refreshTokenExpirationDays;

    public String generateJwt(Authentication auth) {
        Instant now = Instant.now();

        VybesUser user = (VybesUser) auth.getPrincipal();
        String userEmail = user.getEmail();

        String scope =
                auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(" "));

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer(issuer)
                        .issuedAt(now)
                        .expiresAt(now.plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES))
                        .subject(userEmail)
                        .claim("roles", scope)
                        .claim("userId", user.getUserId())
                        .claim("tokenType", "access")
                        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(String email) {
        Instant now = Instant.now();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuer(issuer)
                        .issuedAt(now)
                        .expiresAt(now.plus(refreshTokenExpirationDays, ChronoUnit.DAYS))
                        .subject(email)
                        .claim("tokenType", "refresh")
                        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String extractUsername(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwt jwt = jwtDecoder.decode(refreshToken);

            String tokenType = jwt.getClaimAsString("tokenType");
            if (!"refresh".equals(tokenType)) {
                return false;
            }

            return !jwt.getExpiresAt().isBefore(Instant.now());
        } catch (JwtException e) {
            return false;
        }
    }
}
