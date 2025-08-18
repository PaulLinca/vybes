package com.vybes.dto.mapper;

import com.vybes.dto.ChallengeDTO;
import com.vybes.model.Challenge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChallengeMapper {

    private final UserMapper userMapper;
    private final ChallengeOptionMapper optionMapper;
    private final ChallengeSubmissionMapper submissionMapper;

    public ChallengeDTO transformToDTO(Challenge challenge) {
        return ChallengeDTO.builder()
                .id(challenge.getId())
                .createdBy(userMapper.transformToDTO(challenge.getCreatedBy()))
                .createdAt(challenge.getCreatedAt())
                .expiresAt(challenge.getExpiresAt())
                .question(challenge.getQuestion())
                .type(challenge.getType().name())
                .answerType(challenge.getAnswerType().name())
                .options(
                        Optional.ofNullable(challenge.getOptions())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(
                                        option ->
                                                optionMapper.transformToDTO(
                                                        option, challenge.getAnswerType()))
                                .toList())
                .challengeSubmissions(
                        Optional.ofNullable(challenge.getChallengeSubmissions())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(
                                        submission ->
                                                submissionMapper.transformToDTO(
                                                        submission, challenge.getAnswerType()))
                                .toList())
                .build();
    }
}
