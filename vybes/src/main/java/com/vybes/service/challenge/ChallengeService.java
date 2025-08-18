package com.vybes.service.challenge;

import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.model.*;
import com.vybes.repository.ChallengeRepository;
import com.vybes.repository.ChallengeVoteRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeVoteRepository challengeVoteRepository;
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

    public Challenge getChallenge(Long challengeId) {
        return challengeRepository
                .findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));
    }

    public void voteForOption(Long challengeId, Long optionId, VybesUser user) {
        Challenge challenge =
                challengeRepository
                        .findById(challengeId)
                        .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

        if (userCreatedChallenge(challenge, user)) {
            throw new IllegalArgumentException("User cannot vote for their own challenge");
        }

        if (userAlreadyVoted(challenge, user)) {
            throw new IllegalArgumentException("User has already voted for this challenge");
        }

        challenge.getOptions().stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst()
                .ifPresentOrElse(
                        option -> addVoteToOption(option, user),
                        () -> {
                            throw new IllegalArgumentException("Option not found");
                        });
    }

    private boolean userCreatedChallenge(Challenge challenge, VybesUser user) {
        return challenge.getCreatedBy().equals(user);
    }

    private boolean userAlreadyVoted(Challenge challenge, VybesUser user) {
        return challenge.getOptions().stream()
                .anyMatch(
                        option ->
                                option.getVotes().stream()
                                        .anyMatch(vote -> vote.getUser().equals(user)));
    }

    private void addVoteToOption(ChallengeOption option, VybesUser user) {
        ChallengeVote challengeVote = ChallengeVote.builder().option(option).user(user).build();
        challengeVoteRepository.save(challengeVote);
    }
}
