package com.vybes.service.auth;

import com.vybes.dto.LoginResponseDTO;
import com.vybes.service.user.model.Role;
import com.vybes.service.user.model.VybesUser;
import com.vybes.service.user.repository.RoleRepository;
import com.vybes.service.user.repository.UserRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
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

    public VybesUser registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role role = roleRepository.findByAuthority("USER").orElseThrow();

        VybesUser newUser =
                VybesUser.builder()
                        .username(username)
                        .password(encodedPassword)
                        .authorities(Set.of(role))
                        .build();
        userRepository.save(newUser);

        return newUser;
    }

    public LoginResponseDTO loginUser(String username, String password) {
        try {
            Authentication auth =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));
            String jwtToken = tokenService.generateJwt(auth);

            return LoginResponseDTO.builder()
                    .user(
                            userRepository
                                    .findByUsername(username)
                                    .orElseThrow(
                                            () ->
                                                    new UsernameNotFoundException(
                                                            "Can't find user: " + username)))
                    .jwt(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            return LoginResponseDTO.builder().user(null).jwt("").build();
        }
    }
}
