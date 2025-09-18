package com.vybes.controller;

import com.vybes.dto.ChallengeDTO;
import com.vybes.dto.mapper.ChallengeMapper;
import com.vybes.exception.UserNotFoundException;
import com.vybes.model.FeaturedChallenge;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.challenge.FeaturedChallengeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/featured-challenges")
@RequiredArgsConstructor
public class FeaturedChallengeController {
    private final UserRepository userRepository;
    private final ChallengeMapper challengeMapper;
    private final FeaturedChallengeService featuredChallengeService;

    @GetMapping("/current")
    public ResponseEntity<ChallengeDTO> getFeaturedChallenge() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        return featuredChallengeService
                .getCurrentFeaturedChallenge()
                .map(FeaturedChallenge::getChallenge)
                .map(fc -> challengeMapper.transformToDTO(fc, user.getUserId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
