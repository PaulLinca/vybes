package com.vybes.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class VybeDTO {
    private Long id;
    private UserDTO user;
    private String songName;
    private String spotifyTrackId;
    private List<ArtistDTO> spotifyArtists;
    private String spotifyAlbumId;
    private String imageUrl;
    private ZonedDateTime postedDate;
    private String description;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;
}
