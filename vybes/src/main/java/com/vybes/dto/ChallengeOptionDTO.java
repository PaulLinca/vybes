package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChallengeOptionDTO {
    private Long id;

    private AlbumDTO album;
    private TrackDTO track;
    private ArtistDTO artist;
    private String customText;

    private int votesCount;
    private boolean votedByUser;
}
