package com.vybes.controller;

import com.vybes.dto.FeaturedChallengeDTO;
import com.vybes.dto.mapper.ChallengeMapper;
import com.vybes.dto.request.CreateFeaturedChallengeRequestDTO;
import com.vybes.model.FeaturedChallenge;
import com.vybes.service.challenge.FeaturedChallengeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/featured-challenges")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class FeaturedChallengeAdminController {

    private final ChallengeMapper challengeMapper;
    private final FeaturedChallengeService featuredChallengeService;

    @PostMapping
    public ResponseEntity<FeaturedChallengeDTO> createFeaturedChallenge(
            @RequestBody CreateFeaturedChallengeRequestDTO request) {
        FeaturedChallenge featuredChallenge =
                featuredChallengeService.createFeaturedChallenge(
                        request.getChallengeId(), request.getType(), request.getDurationHours());

        return ResponseEntity.ok(
                FeaturedChallengeDTO.builder()
                        .challenge(
                                challengeMapper.transformToDTO(
                                        featuredChallenge.getChallenge(), null))
                        .featuredAt(featuredChallenge.getFeaturedAt())
                        .featuredUntil(featuredChallenge.getFeaturedUntil())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateFeaturedChallenge(@PathVariable Long id) {
        featuredChallengeService.deactivateCurrentFeaturedChallengeOfType(
                FeaturedChallenge.FeaturedType.DAILY);
        return ResponseEntity.ok().build();
    }
}
