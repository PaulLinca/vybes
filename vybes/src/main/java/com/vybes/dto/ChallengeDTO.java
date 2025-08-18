package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChallengeDTO {
    private Long id;

    private UserDTO createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    private String question;
    private String type;
    private String answerType;

    private List<ChallengeOptionDTO> options;
    private List<ChallengeSubmissionDTO> challengeSubmissions;
}
