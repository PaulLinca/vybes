package com.vybes.service.notification;

import com.vybes.dto.request.RegisterFcmTokenRequestDTO;
import com.vybes.model.FcmToken;
import com.vybes.model.VybesUser;
import com.vybes.repository.FcmTokenRepository;
import com.vybes.repository.UserRepository;
import com.vybes.security.FirebasePrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public void registerToken(FirebasePrincipal principal, RegisterFcmTokenRequestDTO request) {
        VybesUser user =
                userRepository
                        .findByFirebaseUid(principal.uid())
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "User must exist before registering FCM token"));

        String token = request.getToken();
        Optional<FcmToken> existingToken = fcmTokenRepository.findByToken(token);
        if (existingToken.isPresent()) {
            updateExistingToken(request, existingToken.get(), user);
        } else {
            createNewToken(request, token, user);
        }
    }

    private void createNewToken(RegisterFcmTokenRequestDTO request, String token, VybesUser user) {
        fcmTokenRepository.save(
                FcmToken.builder()
                        .token(token)
                        .user(user)
                        .devicePlatform(request.getDevicePlatform())
                        .build());
    }

    private void updateExistingToken(
            RegisterFcmTokenRequestDTO request, FcmToken existingToken, VybesUser user) {
        boolean changed = false;

        if (!existingToken.getUser().getUserId().equals(user.getUserId())) {
            existingToken.setUser(user);
            changed = true;
        }

        if (request.getDevicePlatform() != null
                && !request.getDevicePlatform().equals(existingToken.getDevicePlatform())) {
            existingToken.setDevicePlatform(request.getDevicePlatform());
            changed = true;
        }

        if (changed) {
            fcmTokenRepository.save(existingToken);
        }
    }
}
