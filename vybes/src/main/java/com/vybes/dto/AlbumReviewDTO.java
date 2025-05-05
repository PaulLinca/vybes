package com.vybes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@ToString
public class AlbumReviewDTO {
    private Long id;
    private String albumName;
    private String spotifyAlbumId;
    private Integer score;
    private String imageUrl;
    private ZonedDateTime postedDate;
    private String description;
    private List<ArtistDTO> artists;
    private UserDTO user;
}
