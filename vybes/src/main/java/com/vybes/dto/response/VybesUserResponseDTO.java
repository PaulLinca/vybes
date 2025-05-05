package com.vybes.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import com.vybes.dto.AlbumDTO;
import com.vybes.dto.ArtistDTO;

@Data
@Builder
public class VybesUserResponseDTO {
    private Long userId;
    private String username;
    private String email;
    private Set<ArtistDTO> favoriteArtists;
    private Set<AlbumDTO> favoriteAlbums;
}
