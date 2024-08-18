package com.vybes.service.auth;

import com.vybes.dto.LoginResponseDTO;
import com.vybes.dto.VybesUserResponseDTO;
import com.vybes.exception.UserAlreadyExistsException;
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

    public VybesUserResponseDTO registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role role = roleRepository.findByAuthority("USER").orElseThrow();

        VybesUser newUser =
                VybesUser.builder()
                        .username(username)
                        .password(encodedPassword)
                        .authorities(Set.of(role))
                        .build();
        VybesUser registeredUser = userRepository.save(newUser);

        return VybesUserResponseDTO.builder()
                .username(registeredUser.getUsername())
                .userId(registeredUser.getUserId())
                .build();
    }

    public LoginResponseDTO loginUser(String username, String password) {
        try {
            Authentication auth =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));
            String jwtToken = tokenService.generateJwt(auth);

            VybesUser user =
                    userRepository
                            .findByUsername(username)
                            .orElseThrow(
                                    () ->
                                            new UsernameNotFoundException(
                                                    "Can't find user: " + username));

            return LoginResponseDTO.builder()
                    .username(user.getUsername())
                    .userId(user.getUserId())
                    .jwt(jwtToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
