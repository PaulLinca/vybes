package com.vybes.dto.request;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CreateVybeRequestDTO {
    private String spotifyTrackId;
    private ZonedDateTime postedDate;
    private String description;
}
