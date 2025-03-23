package com.vybes.service.spotify.model.search;

import com.vybes.service.spotify.model.entity.SpotifyEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor // we need this for jackson deserializing
public class SearchTrackItem extends SpotifyEntity {
    private String artist;
    private String album;
    private String imageUrl;
}
