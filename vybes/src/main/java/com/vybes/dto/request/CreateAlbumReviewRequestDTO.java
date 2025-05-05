package com.vybes.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAlbumReviewRequestDTO {
    private String spotifyAlbumId;
    private Integer score;
    private String description;
}
