package com.vybes.service.challenge;

import com.vybes.model.Challenge;
import com.vybes.model.FeaturedChallenge;
import com.vybes.repository.ChallengeRepository;
import com.vybes.repository.FeaturedChallengeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeaturedChallengeService {
    private final ChallengeRepository challengeRepository;
    private final FeaturedChallengeRepository featuredChallengeRepository;

    public Optional<FeaturedChallenge> getCurrentFeaturedChallenge() {
        return featuredChallengeRepository.findCurrentFeaturedChallenge(ZonedDateTime.now());
    }

    public FeaturedChallenge createFeaturedChallenge(
            Long challengeId, FeaturedChallenge.FeaturedType type, int durationHours) {
        Challenge challenge =
                challengeRepository
                        .findById(challengeId)
                        .orElseThrow(() -> new RuntimeException("Challenge not found"));

        if (featuredChallengeRepository.isChallengeFeatured(challenge, ZonedDateTime.now())) {
            throw new RuntimeException("Challenge is already featured");
        }

        deactivateCurrentFeaturedChallengeOfType(type);

        ZonedDateTime now = ZonedDateTime.now();
        FeaturedChallenge featuredChallenge =
                FeaturedChallenge.builder()
                        .challenge(challenge)
                        .featuredAt(now)
                        .featuredUntil(now.plusHours(durationHours))
                        .featuredType(type)
                        .isActive(true)
                        .build();

        log.info("Created featured challenge: {} for {} hours", challengeId, durationHours);
        return featuredChallengeRepository.save(featuredChallenge);
    }

    public void deactivateCurrentFeaturedChallengeOfType(FeaturedChallenge.FeaturedType type) {
        Optional<FeaturedChallenge> current =
                featuredChallengeRepository.findCurrentFeaturedChallenge(ZonedDateTime.now());
        if (current.isPresent() && current.get().getFeaturedType() == type) {
            FeaturedChallenge featuredChallenge = current.get();
            featuredChallenge.setIsActive(false);
            featuredChallengeRepository.save(featuredChallenge);
            log.info("Deactivated current featured challenge: {}", featuredChallenge.getId());
        }
    }
}
