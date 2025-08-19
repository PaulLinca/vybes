package com.vybes.service.challenge;

import com.vybes.dto.request.ChallengeOptionRequestDTO;
import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.model.*;
import com.vybes.repository.ChallengeOptionRepository;
import com.vybes.repository.ChallengeRepository;
import com.vybes.repository.ChallengeSubmissionRepository;
import com.vybes.repository.ChallengeVoteRepository;
import com.vybes.service.music.MusicService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChallengeService {

    private final MusicService musicService;
    private final ChallengeRepository challengeRepository;
    private final ChallengeVoteRepository challengeVoteRepository;
    private final ChallengeOptionRepository challengeOptionRepository;
    private final ChallengeSubmissionRepository challengeSubmissionRepository;
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

    public ChallengeSubmission addSubmission(
            Long challengeId, ChallengeOptionRequestDTO request, VybesUser user) {
        Challenge challenge =
                challengeRepository
                        .findById(challengeId)
                        .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

        if (challenge.getType() != ChallengeType.USER_SUBMISSIONS) {
            throw new IllegalArgumentException("Challenge does not accept submissions");
        }

        if (userCreatedChallenge(challenge, user)) {
            throw new IllegalArgumentException("User cannot vote for their own challenge");
        }

        if (userAlreadySubmitted(challenge, user)) {
            throw new IllegalArgumentException("User has already submitted to this challenge");
        }

        ChallengeSubmission submission =
                ChallengeSubmission.builder()
                        .challenge(challenge)
                        .user(user)
                        .submittedAt(LocalDateTime.now())
                        .build();

        switch (challenge.getAnswerType()) {
            case ALBUM:
                submission.setAlbumId(
                        musicService.getOrCreateAlbum(request.getSpotifyAlbumId()).getId());
                break;
            case TRACK:
                submission.setTrackId(
                        musicService.getOrCreateTrack(request.getSpotifyTrackId()).getId());
                break;
            case ARTIST:
                submission.setArtistId(
                        musicService.getOrCreateArtist(request.getSpotifyArtistId()).getId());
                break;
            case CUSTOM_TEXT:
                submission.setCustomText(request.getCustomText());
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported answer type: " + challenge.getAnswerType());
        }

        return challengeSubmissionRepository.save(submission);
    }

    public List<ChallengeSubmission> getSubmissions(Long challengeId) {
        return challengeSubmissionRepository.findAllByChallengeId(challengeId);
    }

    public void voteForOption(Long challengeId, Long optionId, VybesUser user) {
        ChallengeOption option =
                challengeOptionRepository
                        .findById(optionId)
                        .orElseThrow(() -> new IllegalArgumentException("Option not found"));

        if (!option.getChallenge().getId().equals(challengeId)) {
            throw new IllegalArgumentException("Option does not belong to this challenge");
        }

        if (userCreatedChallenge(option.getChallenge(), user)) {
            throw new IllegalArgumentException("User cannot vote for their own challenge");
        }

        if (userAlreadyVotedOption(option.getChallenge(), user)) {
            throw new IllegalArgumentException("User has already voted for this challenge");
        }

        addVoteToOption(option, user);
    }

    public void voteForSubmission(Long challengeId, Long submissionId, VybesUser user) {
        ChallengeSubmission submission =
                challengeSubmissionRepository
                        .findById(submissionId)
                        .orElseThrow(() -> new IllegalArgumentException("Submission not found"));

        if (!submission.getChallenge().getId().equals(challengeId)) {
            throw new IllegalArgumentException("Submission does not belong to this challenge");
        }

        if (userCreatedChallenge(submission.getChallenge(), user)) {
            throw new IllegalArgumentException("User cannot vote for their own challenge");
        }

        if (userAlreadyVotedSubmission(submission.getChallenge(), user)) {
            throw new IllegalArgumentException("User has already voted for this challenge");
        }

        addVoteToSubmission(submission, user);
    }

    private void addVoteToOption(ChallengeOption option, VybesUser user) {
        ChallengeVote challengeVote = ChallengeVote.builder().option(option).user(user).build();
        challengeVoteRepository.save(challengeVote);
    }

    private void addVoteToSubmission(ChallengeSubmission submission, VybesUser user) {
        ChallengeVote challengeVote =
                ChallengeVote.builder().submission(submission).user(user).build();
        challengeVoteRepository.save(challengeVote);
    }

    private boolean userAlreadySubmitted(Challenge challenge, VybesUser user) {
        return challenge.getChallengeSubmissions().stream()
                .anyMatch(submission -> submission.getUser().equals(user));
    }

    private boolean userCreatedChallenge(Challenge challenge, VybesUser user) {
        return challenge.getCreatedBy().equals(user);
    }

    private boolean userAlreadyVotedOption(Challenge challenge, VybesUser user) {
        return challenge.getOptions().stream()
                .anyMatch(
                        option ->
                                option.getVotes().stream()
                                        .anyMatch(vote -> vote.getUser().equals(user)));
    }

    private boolean userAlreadyVotedSubmission(Challenge challenge, VybesUser user) {
        return challenge.getChallengeSubmissions().stream()
                .anyMatch(
                        submission ->
                                submission.getVotes().stream()
                                        .anyMatch(vote -> vote.getUser().equals(user)));
    }
}
