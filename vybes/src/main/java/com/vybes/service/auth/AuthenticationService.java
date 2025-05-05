package com.vybes.service.auth;

import com.vybes.dto.response.LoginResponseDTO;
import com.vybes.dto.response.VybesUserResponseDTO;
import com.vybes.exception.EmailAlreadyUsedException;
import com.vybes.model.Role;
import com.vybes.model.VybesUser;
import com.vybes.repository.RoleRepository;
import com.vybes.repository.UserRepository;

import io.micrometer.common.util.StringUtils;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public VybesUserResponseDTO registerUser(String email, String password) {
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            throw new BadRequestException("Email and password are required");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyUsedException("Email address already in use");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role role =
                roleRepository
                        .findByAuthority("USER")
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Required USER role not found in database"));

        VybesUser newUser =
                VybesUser.builder()
                        .email(email)
                        .password(encodedPassword)
                        .authorities(Set.of(role))
                        .build();
        VybesUser registeredUser = userRepository.save(newUser);

        return VybesUserResponseDTO.builder()
                .email(registeredUser.getEmail())
                .userId(registeredUser.getUserId())
                .build();
    }

    public LoginResponseDTO loginUser(String email, String password) {
        VybesUser user = getUserFromEmail(email);

        try {
            Authentication auth =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(email, password));

            userRepository.save(user);

            String jwtToken = tokenService.generateJwt(auth);
            String refreshToken = tokenService.generateRefreshToken(email);

            return buildLoginResponse(user, jwtToken, refreshToken);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        try {
            if (!tokenService.validateRefreshToken(refreshToken)) {
                throw new InvalidTokenException("Refresh token is invalid or expired");
            }

            String email = tokenService.extractUsername(refreshToken);
            VybesUser user = getUserFromEmail(email);

            Authentication auth =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            String newJwt = tokenService.generateJwt(auth);
            String newRefreshToken = tokenService.generateRefreshToken(email);

            return buildLoginResponse(user, newJwt, newRefreshToken);
        } catch (Exception e) {
            throw new InvalidTokenException("Failed to refresh token: " + e.getMessage());
        }
    }

    private VybesUser getUserFromEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    private LoginResponseDTO buildLoginResponse(VybesUser user, String jwt, String refreshToken) {
        return LoginResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .userId(user.getUserId())
                .jwt(jwt)
                .refreshToken(refreshToken)
                .requiresUsernameSetup(user.getUsername() == null)
                .build();
    }

    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }

    public static class BadRequestException extends RuntimeException {
        public BadRequestException(String message) {
            super(message);
        }
    }
}
