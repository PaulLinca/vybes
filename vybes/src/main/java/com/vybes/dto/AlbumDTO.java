package com.vybes.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumDTO {
    private String spotifyId;
    private String name;
    private String imageUrl;
    private List<ArtistDTO> artists;
}
