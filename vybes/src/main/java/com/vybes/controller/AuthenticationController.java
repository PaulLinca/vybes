package com.vybes.controller;

import com.vybes.dto.LoginResponseDTO;
import com.vybes.dto.VybesUserResponseDTO;
import com.vybes.dto.request.AuthRequestDTO;
import com.vybes.service.auth.AuthenticationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*") // check what this means
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<VybesUserResponseDTO> registerUser(
            @Valid @RequestBody AuthRequestDTO authRequestDTO) {

        VybesUserResponseDTO user =
                authenticationService.registerUser(
                        authRequestDTO.getEmail(), authRequestDTO.getPassword());

        log.info("User registered successfully: {}", authRequestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(
            @Valid @RequestBody AuthRequestDTO authRequestDTO) {
        try {
            LoginResponseDTO response =
                    authenticationService.loginUser(
                            authRequestDTO.getEmail(), authRequestDTO.getPassword());

            log.info("User logged in successfully: {}", authRequestDTO.getEmail());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            log.warn("Failed login attempt for email: {}", authRequestDTO.getEmail());
            throw e;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(
            @RequestHeader("Authorization") String refreshToken) {

        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        try {
            LoginResponseDTO response = authenticationService.refreshToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.warn("Token refresh failed: {}", e.getMessage());
            throw e;
        }
    }
}
