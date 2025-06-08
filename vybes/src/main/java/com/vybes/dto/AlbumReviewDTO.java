package com.vybes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@ToString
public class AlbumReviewDTO implements PostDTO {
    private Long id;
    private String albumName;
    private String spotifyId;
    private Integer score;
    private String imageUrl;
    private String description;
    private List<ArtistDTO> artists;
    private UserDTO user;
    private ZonedDateTime postedDate;
    private LocalDate releaseDate;
    private List<TrackReviewDTO> trackReviews;
    private List<LikeDTO> likes;
    private List<CommentDTO> comments;
}
