package com.vybes.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ChallengeSubmissionDTO {
    private Long id;
    private UserDTO user;
    private LocalDateTime submittedAt;

    private AlbumDTO album;
    private TrackDTO track;
    private ArtistDTO artist;
    private String customText;

    private int votesCount;
    private boolean votedByUser;
}
