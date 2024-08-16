package com.vybes.service.vybe.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class VybeDTO {
    private Long id;
    private Long userId;
    private String songName;
    private String spotifyTrackId;
    private List<String> spotifyArtistIds;
    private String spotifyAlbumId;
    private String imageUrl;
    private ZonedDateTime postedDate;
    private List<Long> likeIds;
    private List<Long> commentIds;
}
