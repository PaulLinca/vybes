package com.vybes.dto.request;

import lombok.Data;

@Data
public class CreateVybeRequestDTO {
    private String spotifyTrackId;
    private String description;
}
