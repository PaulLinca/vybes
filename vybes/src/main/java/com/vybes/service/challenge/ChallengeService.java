package com.vybes.service.challenge;

import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.model.Challenge;
import com.vybes.model.ChallengeType;
import com.vybes.model.VybesUser;
import com.vybes.repository.ChallengeRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeOptionResolver challengeOptionResolver;

    public Challenge createChallenge(SubmitChallengeRequestDTO request, VybesUser user) {
        Challenge challenge =
                Challenge.builder()
                        .question(request.getQuestion())
                        .createdBy(user)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(request.getExpiresAt())
                        .answerType(request.getAnswerType())
                        .type(request.getChallengeType())
                        .build();

        if (request.getChallengeType() == ChallengeType.POLL) {
            challenge.setOptions(
                    challengeOptionResolver.resolveOptions(
                            request.getOptions(), request.getAnswerType(), challenge));
        }

        return challengeRepository.save(challenge);
    }
}
