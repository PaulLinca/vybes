package com.vybes.service.spotify.model.search.artist;

import com.vybes.service.spotify.model.entity.SpotifyEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class SearchArtistItem extends SpotifyEntity {
    private String imageUrl;
}
