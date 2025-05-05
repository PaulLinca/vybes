package com.vybes.external.spotify.model.search.track;

import com.vybes.external.spotify.model.entity.SpotifyEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor // we need this for jackson deserializing
public class SearchTrackResult extends SpotifyEntity {
    private String artist;
    private String album;
    private String imageUrl;
}
