package com.vybes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class AlbumReviewDTO implements PostDTO {
    private Long id;
    private String albumName;
    private String spotifyAlbumId;
    private Integer score;
    private String imageUrl;
    private String description;
    private List<ArtistDTO> artists;
    private UserDTO user;
}
