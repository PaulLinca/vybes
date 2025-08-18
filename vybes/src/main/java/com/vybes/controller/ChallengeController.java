package com.vybes.controller;

import com.vybes.dto.ChallengeDTO;
import com.vybes.dto.mapper.ChallengeMapper;
import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.exception.UserNotFoundException;
import com.vybes.model.Challenge;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.challenge.ChallengeService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final UserRepository userRepository;
    private final ChallengeService challengeService;
    private final ChallengeMapper challengeMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ChallengeDTO submitChallenge(@Valid @RequestBody SubmitChallengeRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        Challenge challenge = challengeService.createChallenge(request, user);

        return challengeMapper.transformToDTO(challenge);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{challengeId}", produces = "application/json; charset=UTF-8")
    public ChallengeDTO getChallenge(@PathVariable Long challengeId) {
        Challenge challenge = challengeService.getChallenge(challengeId);

        return challengeMapper.transformToDTO(challenge);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            value = "/{challengeId}/options/{optionId}/vote",
            produces = "application/json; charset=UTF-8")
    public void voteForOption(@PathVariable Long challengeId, @PathVariable Long optionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        challengeService.voteForOption(challengeId, optionId, user);
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
