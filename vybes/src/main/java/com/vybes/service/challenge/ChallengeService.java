package com.vybes.service.challenge;

import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.dto.response.ChallengeResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeService {

    public ChallengeResponseDTO createChallenge(
            SubmitChallengeRequestDTO request, String username) {
        return null;
    }
}
