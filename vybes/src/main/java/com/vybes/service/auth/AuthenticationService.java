package com.vybes.service.auth;

import com.vybes.dto.LoginResponseDTO;
import com.vybes.dto.VybesUserResponseDTO;
import com.vybes.exception.EmailAlreadyUsedException;
import com.vybes.service.user.model.Role;
import com.vybes.service.user.model.VybesUser;
import com.vybes.service.user.repository.RoleRepository;
import com.vybes.service.user.repository.UserRepository;

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
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyUsedException("Email address already used.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role role = roleRepository.findByAuthority("USER").orElseThrow();

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
        try {
            Authentication auth =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(email, password));
            String jwtToken = tokenService.generateJwt(auth);
            String refreshToken = tokenService.generateRefreshToken(email);

            VybesUser user =
                    userRepository
                            .findByEmail(email)
                            .orElseThrow(
                                    () ->
                                            new UsernameNotFoundException(
                                                    "Can't find user: " + email));

            return LoginResponseDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .userId(user.getUserId())
                    .jwt(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        String username = tokenService.extractUsername(refreshToken);
        VybesUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find user: " + username));

        String newJwt = tokenService.generateJwt(new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities()));
        String newRefreshToken = tokenService.generateRefreshToken(username);

        return LoginResponseDTO.builder()
                .username(user.getUsername())
                .userId(user.getUserId())
                .jwt(newJwt)
                .refreshToken(newRefreshToken)
                .build();
    }
}
