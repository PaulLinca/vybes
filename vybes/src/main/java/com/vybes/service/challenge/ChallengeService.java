package com.vybes.service.challenge;

import com.vybes.dto.request.ChallengeOptionRequestDTO;
import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.dto.response.ChallengeResponseDTO;

import com.vybes.exception.UserNotFoundException;
import com.vybes.external.spotify.SpotifyService;
import com.vybes.model.*;
import com.vybes.repository.*;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChallengeService {

    public ChallengeResponseDTO createChallenge(
            SubmitChallengeRequestDTO request, String username) {
        return null;
    }
}
