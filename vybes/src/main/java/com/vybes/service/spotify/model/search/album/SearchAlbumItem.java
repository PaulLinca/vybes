package com.vybes.service.spotify.model.search.album;

import com.vybes.service.spotify.model.entity.SpotifyEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class SearchAlbumItem extends SpotifyEntity {
    private String imageUrl;
}
