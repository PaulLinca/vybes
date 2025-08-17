package com.vybes.controller;

import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.dto.response.ChallengeResponseDTO;
import com.vybes.service.challenge.ChallengeService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<ChallengeResponseDTO> submitChallenge(
            @Valid @RequestBody SubmitChallengeRequestDTO request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ChallengeResponseDTO response = challengeService.createChallenge(request, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
