package com.vybes.controller;

import com.vybes.dto.ChallengeDTO;
import com.vybes.dto.mapper.ChallengeMapper;
import com.vybes.model.FeaturedChallenge;
import com.vybes.service.challenge.FeaturedChallengeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/featured-challenges")
@RequiredArgsConstructor
public class FeaturedChallengeController {

    private final ChallengeMapper challengeMapper;
    private final FeaturedChallengeService featuredChallengeService;

    @GetMapping("/current")
    public ResponseEntity<ChallengeDTO> getFeaturedChallenge() {
        return featuredChallengeService
                .getCurrentFeaturedChallenge()
                .map(FeaturedChallenge::getChallenge)
                .map(challengeMapper::transformToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
