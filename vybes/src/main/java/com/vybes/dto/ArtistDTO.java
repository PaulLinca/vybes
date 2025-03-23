package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistDTO {
    private String spotifyId;
    private String name;
    private String imageUrl;
}
