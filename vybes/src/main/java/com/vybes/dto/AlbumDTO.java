package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AlbumDTO {
    private String spotifyId;
    private String name;
    private String imageUrl;
    private List<ArtistDTO> artists;
    private List<TrackDTO> tracks;
    private LocalDate releaseDate;
}
