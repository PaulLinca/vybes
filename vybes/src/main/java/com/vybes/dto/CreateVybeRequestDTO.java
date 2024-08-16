package com.vybes.dto;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;

@Data
public class CreateVybeRequestDTO {
    private String songName;
    private String spotifyTrackId;
    private List<String> spotifyArtistIds;
    private String spotifyAlbumId;
    private String imageUrl;
    private Long userId;
    private ZonedDateTime postedDate;
}
