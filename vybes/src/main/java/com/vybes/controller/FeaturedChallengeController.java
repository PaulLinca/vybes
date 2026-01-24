package com.vybes.controller;

import com.vybes.dto.FeaturedChallengeDTO;
import com.vybes.dto.mapper.ChallengeMapper;
import com.vybes.exception.UserNotFoundException;
import com.vybes.model.FeaturedChallenge;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.challenge.FeaturedChallengeService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/featured-challenges")
@RequiredArgsConstructor
public class FeaturedChallengeController {

    private final UserRepository userRepository;
    private final ChallengeMapper challengeMapper;
    private final FeaturedChallengeService featuredChallengeService;

    @GetMapping("/current")
    public ResponseEntity<FeaturedChallengeDTO> getFeaturedChallenge() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        return featuredChallengeService
                .getCurrentFeaturedChallenge()
                .map(fc -> challengeMapper.transformToDTO(fc, user.getUserId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/history")
    public ResponseEntity<List<FeaturedChallengeDTO>> getPastFeaturedChallenges(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        int sanitizedPage = page < 0 ? 0 : page;
        int sanitizedSize = size < 1 ? 1 : Math.min(size, 100);
        Pageable pageable = PageRequest.of(sanitizedPage, sanitizedSize);

        Page<FeaturedChallenge> pastChallengesPage =
                featuredChallengeService.getPastFeaturedChallenges(pageable);

        List<FeaturedChallengeDTO> dtos = pastChallengesPage.getContent().stream()
                .map(fc -> challengeMapper.transformToDTO(fc, user.getUserId()))
                .toList();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Elements", String.valueOf(pastChallengesPage.getTotalElements()));
        headers.add("X-Total-Pages", String.valueOf(pastChallengesPage.getTotalPages()));
        headers.add("X-Page", String.valueOf(pastChallengesPage.getNumber()));
        headers.add("X-Size", String.valueOf(pastChallengesPage.getSize()));

        return ResponseEntity.ok().headers(headers).body(dtos);
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
