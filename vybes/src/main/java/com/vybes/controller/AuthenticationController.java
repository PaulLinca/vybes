package com.vybes.controller;

import com.vybes.dto.response.LoginResponseDTO;
import com.vybes.security.FirebasePrincipal;
import com.vybes.service.auth.FirebaseAuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final FirebaseAuthenticationService firebaseAuthService;

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDTO> authenticate(Authentication authentication) {
        FirebasePrincipal principal = (FirebasePrincipal) authentication.getPrincipal();
        LoginResponseDTO dto = firebaseAuthService.authenticateOrCreate(principal);
        log.info(
                "Firebase user authenticated (uid={} email={})",
                principal.uid(),
                principal.email());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        FirebasePrincipal principal = (FirebasePrincipal) authentication.getPrincipal();
        firebaseAuthService.logout(principal);
        return ResponseEntity.noContent().build();
    }
}
