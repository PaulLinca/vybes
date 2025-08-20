package com.vybes.dto.request;

import com.vybes.model.FeaturedChallenge;
import lombok.Data;

@Data
public class CreateFeaturedChallengeRequestDTO {
    private Long challengeId;
    private FeaturedChallenge.FeaturedType type;
    private int durationHours;
    private String reason;
}
