package com.vybes.external.spotify.model.search.album;

import com.vybes.external.spotify.model.entity.SpotifyEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class SearchAlbumResult extends SpotifyEntity {
    private String imageUrl;
}
