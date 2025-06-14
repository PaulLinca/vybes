package com.vybes.dto.response;

import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class VybesUserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private String profilePictureUrl;
    private Set<ArtistDTO> favoriteArtists;
    private Set<AlbumDTO> favoriteAlbums;
}
