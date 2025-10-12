package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class FeaturedChallengeDTO {
    private ChallengeDTO challenge;
    private ZonedDateTime featuredAt;
    private ZonedDateTime featuredUntil;
}
