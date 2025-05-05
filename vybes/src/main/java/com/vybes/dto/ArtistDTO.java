package com.vybes.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ArtistDTO {
    private String spotifyId;
    private String name;
    private String imageUrl;
}
