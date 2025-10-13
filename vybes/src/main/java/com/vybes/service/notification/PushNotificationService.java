package com.vybes.service.notification;

import com.google.firebase.messaging.*;
import com.vybes.model.Challenge;
import com.vybes.model.FcmToken;
import com.vybes.model.FeaturedChallenge;
import com.vybes.repository.FcmTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PushNotificationService {

    private static final int FCM_BATCH_LIMIT = 500;

    private final FcmTokenRepository fcmTokenRepository;

    @Transactional(readOnly = true)
    public void sendNewFeaturedChallengeNotificationAsync(FeaturedChallenge featured) {
        new Thread(
                        () -> {
                            try {
                                sendNewFeaturedChallengeNotification(featured);
                            } catch (Exception e) {
                                log.warn(
                                        "Failed to send featured challenge notification: {}",
                                        e.getMessage());
                            }
                        },
                        "featured-challenge-push")
                .start();
    }

    @Transactional
    public void sendNewFeaturedChallengeNotification(FeaturedChallenge featured) {
        List<FcmToken> tokens = fcmTokenRepository.findAll();
        if (tokens.isEmpty()) {
            log.debug(
                    "No FCM tokens registered; skipping notification for featured challenge {}",
                    featured.getId());
            return;
        }

        Map<String, FcmToken> tokenMap = new LinkedHashMap<>();
        for (FcmToken t : tokens) {
            tokenMap.putIfAbsent(t.getToken(), t);
        }
        List<String> allTokens = new ArrayList<>(tokenMap.keySet());

        String question =
                Optional.ofNullable(featured.getChallenge())
                        .map(Challenge::getQuestion)
                        .orElse("A new challenge is live!");
        String shortQuestion = truncate(question, 80);
        String title =
                switch (featured.getFeaturedType()) {
                    case DAILY -> "New Daily Challenge";
                    case WEEKLY -> "New Weekly Challenge";
                    case SPECIAL_EVENT -> "Special Event Challenge";
                };
        String body = shortQuestion + " - Tap to vote now!";

        int total = allTokens.size();
        int sent = 0;
        int removed = 0;
        for (int i = 0; i < allTokens.size(); i += FCM_BATCH_LIMIT) {
            List<String> batch =
                    allTokens.subList(i, Math.min(i + FCM_BATCH_LIMIT, allTokens.size()));
            MulticastMessage message =
                    MulticastMessage.builder()
                            .addAllTokens(batch)
                            .putData("title", title)
                            .putData("body", body)
                            .putData("challengeId", String.valueOf(featured.getChallenge().getId()))
                            .putData("featuredId", String.valueOf(featured.getId()))
                            .putData("type", featured.getFeaturedType().name())
                            .build();
            try {
                BatchResponse response;
                try {
                    response = FirebaseMessaging.getInstance().sendMulticast(message);
                } catch (FirebaseMessagingException primary) {
                    if (isBatchNotFound(primary)) {
                        // Fallback: retry sending each token individually
                        log.warn(
                                "Multicast batch endpoint returned 404; falling back to sendEachForMulticast. Cause: {}",
                                sanitize(primary.getMessage()));
                        try {
                            response =
                                    FirebaseMessaging.getInstance().sendEachForMulticast(message);
                        } catch (FirebaseMessagingException secondary) {
                            log.warn(
                                    "Fallback sendEachForMulticast also failed: {}",
                                    sanitize(secondary.getMessage()));
                            continue;
                        }
                    } else {
                        log.warn(
                                "Firebase messaging batch failed: {}",
                                sanitize(primary.getMessage()));
                        continue;
                    }
                }
                sent += response.getSuccessCount();
                if (response.getFailureCount() > 0) {
                    List<String> invalidTokensToRemove = collectInvalidTokens(response, batch);
                    if (!invalidTokensToRemove.isEmpty()) {
                        removed += invalidTokensToRemove.size();
                        removeInvalidTokens(invalidTokensToRemove);
                    }
                }
            } catch (Exception e) {
                log.warn("Unexpected error while sending FCM batch: {}", sanitize(e.getMessage()));
            }
        }
        log.info(
                "Featured challenge notification summary: totalTokens={} sent={} removedInvalid={} featuredId={}",
                total,
                sent,
                removed,
                featured.getId());
    }

    private boolean isBatchNotFound(FirebaseMessagingException e) {
        String msg = e.getMessage();
        if (msg == null) return false;
        return msg.contains("404") && msg.contains("/batch");
    }

    private List<String> collectInvalidTokens(BatchResponse response, List<String> batch) {
        List<String> invalidTokensToRemove = new ArrayList<>();
        for (int idx = 0; idx < response.getResponses().size(); idx++) {
            var resp = response.getResponses().get(idx);
            if (!resp.isSuccessful() && resp.getException() != null) {
                var code = resp.getException().getMessagingErrorCode();
                if (code != null && isUnrecoverableTokenError(code.name())) {
                    invalidTokensToRemove.add(batch.get(idx));
                }
            }
        }
        return invalidTokensToRemove;
    }

    private void removeInvalidTokens(List<String> invalidTokensToRemove) {
        for (String invalidToken : invalidTokensToRemove) {
            try {
                fcmTokenRepository.findByToken(invalidToken).ifPresent(fcmTokenRepository::delete);
            } catch (Exception ex) {
                log.debug(
                        "Failed to remove invalid FCM token {}: {}",
                        invalidToken,
                        sanitize(ex.getMessage()));
            }
        }
    }

    private String sanitize(String s) {
        if (s == null) {
            return null;
        }
        return s.length() > 300 ? s.substring(0, 300) + "..." : s;
    }

    private boolean isUnrecoverableTokenError(String code) {
        return code.equalsIgnoreCase("UNREGISTERED")
                || code.equalsIgnoreCase("INVALID_ARGUMENT")
                || code.equalsIgnoreCase("INVALID_REGISTRATION")
                || code.equalsIgnoreCase("REGISTRATION_TOKEN_NOT_REGISTERED");
    }

    private String truncate(String s, int max) {
        if (s == null) {
            return "";
        }
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
