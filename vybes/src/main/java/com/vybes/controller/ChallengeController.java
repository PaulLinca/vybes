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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final UserRepository userRepository;
    private final ChallengeService challengeService;
    private final ChallengeMapper challengeMapper;

    @PostMapping
    public ResponseEntity<ChallengeDTO> submitChallenge(
            @Valid @RequestBody SubmitChallengeRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        Challenge challenge = challengeService.createChallenge(request, user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(challengeMapper.transformToDTO(challenge));
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
