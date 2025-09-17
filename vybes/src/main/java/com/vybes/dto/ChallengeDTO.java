package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ChallengeDTO {
    private Long id;

    private UserDTO createdBy;
    private ZonedDateTime createdAt;

    private String question;
    private String type;
    private String answerType;

    private List<ChallengeOptionDTO> options = new ArrayList<>();
    private List<ChallengeSubmissionDTO> challengeSubmissions = new ArrayList<>();
}
