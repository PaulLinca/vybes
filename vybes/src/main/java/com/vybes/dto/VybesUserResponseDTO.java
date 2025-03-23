package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class VybesUserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private Set<ArtistDTO> favoriteArtists;
    private Set<AlbumDTO> favoriteAlbums;
}
