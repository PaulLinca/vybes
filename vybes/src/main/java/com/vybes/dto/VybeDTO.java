package com.vybes.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class VybeDTO implements PostDTO {
    private Long id;
    private UserDTO user;
    private String songName;
    private String spotifyId;
    private List<ArtistDTO> spotifyArtists;
    private String spotifyAlbumId;
    private String imageUrl;
    private String description;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;
    private ZonedDateTime postedDate;
}
