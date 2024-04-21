package com.vybes.service.spotify.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class SpotifyEntity {
    private String id;
    private String name;
    private String spotifyUrl;
}
