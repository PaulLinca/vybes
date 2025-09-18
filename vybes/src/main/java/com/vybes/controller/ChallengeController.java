package com.vybes.controller;

import com.vybes.dto.ChallengeDTO;
import com.vybes.dto.ChallengeSubmissionDTO;
import com.vybes.dto.mapper.ChallengeMapper;
import com.vybes.dto.mapper.ChallengeSubmissionMapper;
import com.vybes.dto.request.ChallengeOptionRequestDTO;
import com.vybes.dto.request.SubmitChallengeRequestDTO;
import com.vybes.exception.UserNotFoundException;
import com.vybes.model.AnswerType;
import com.vybes.model.Challenge;
import com.vybes.model.ChallengeSubmission;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.challenge.ChallengeService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {
    private final UserRepository userRepository;
    private final ChallengeService challengeService;
    private final ChallengeMapper challengeMapper;
    private final ChallengeSubmissionMapper challengeSubmissionMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ChallengeDTO submitChallenge(@Valid @RequestBody SubmitChallengeRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        Challenge challenge = challengeService.createChallenge(request, user);

        return challengeMapper.transformToDTO(challenge);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{challengeId}/submissions", produces = "application/json; charset=UTF-8")
    public ChallengeSubmissionDTO addSubmission(
            @PathVariable Long challengeId, @Valid @RequestBody ChallengeOptionRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        ChallengeSubmission submission = challengeService.addSubmission(challengeId, request, user);

        return challengeSubmissionMapper.transformToDTO(
                submission, submission.getChallenge().getAnswerType());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/{challengeId}/submissions", produces = "application/json; charset=UTF-8")
    public List<ChallengeSubmissionDTO> getSubmissions(@PathVariable Long challengeId) {
        List<ChallengeSubmission> submissions = challengeService.getSubmissions(challengeId);

        if (submissions.isEmpty()) {
            return List.of();
        }

        AnswerType answerType =
                submissions.stream()
                        .map(ChallengeSubmission::getChallenge)
                        .map(Challenge::getAnswerType)
                        .findFirst()
                        .get();

        return submissions.stream()
                .map(s -> challengeSubmissionMapper.transformToDTO(s, answerType))
                .collect(Collectors.toList());
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
    public ChallengeDTO voteForOption(@PathVariable Long challengeId, @PathVariable Long optionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        return challengeMapper.transformToDTO(
                challengeService.voteForOption(challengeId, optionId, user));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            value = "/{challengeId}/submission/{submissionId}/vote",
            produces = "application/json; charset=UTF-8")
    public void voteForSubmission(@PathVariable Long challengeId, @PathVariable Long submissionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = getUser(authentication.getName());

        challengeService.voteForSubmission(challengeId, submissionId, user);
    }

    private VybesUser getUser(String name) {
        return userRepository
                .findByEmail(name)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + name));
    }
}
