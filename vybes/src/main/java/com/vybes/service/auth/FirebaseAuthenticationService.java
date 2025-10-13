package com.vybes.service.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.vybes.dto.response.LoginResponseDTO;
import com.vybes.model.Role;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.security.FirebasePrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseAuthenticationService {

    private final UserRepository userRepository;

    @Transactional
    public LoginResponseDTO authenticateOrCreate(FirebasePrincipal principal) {
        String email = principal.email();
        if (email == null) {
            try {
                UserRecord record = FirebaseAuth.getInstance().getUser(principal.uid());
                email = record.getEmail();
            } catch (Exception e) {
                log.warn("Email not present for uid={} and lookup failed: {}", principal.uid(), e.getMessage());
            }
        }
        if (email != null) {
            email = email.toLowerCase();
        }

        String finalEmail = email;
        VybesUser user = userRepository.findByFirebaseUid(principal.uid()).orElseGet(() ->
                userRepository.save(VybesUser.builder()
                        .firebaseUid(principal.uid())
                        .email(finalEmail)
                        .build())
        );

        if (email != null && (user.getEmail() == null || !user.getEmail().equalsIgnoreCase(email))) {
            user.setEmail(email);
            user = userRepository.save(user);
        }

        return LoginResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .userId(user.getUserId())
                .roles(
                        user.getAuthorities().stream()
                                .map(Role::getAuthority)
                                .collect(Collectors.toSet()))
                .requiresUsernameSetup(user.getUsername() == null)
                .build();
    }

    public void logout(FirebasePrincipal principal) {
        try {
            FirebaseAuth.getInstance().revokeRefreshTokens(principal.uid());
            log.info("Revoked refresh tokens for uid={}", principal.uid());
        } catch (Exception e) {
            log.warn("Failed to revoke refresh tokens for uid={} : {}", principal.uid(), e.getMessage());
        }
    }
}
