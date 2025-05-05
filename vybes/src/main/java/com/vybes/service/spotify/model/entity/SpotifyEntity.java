package com.vybes.service.spotify.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
public abstract class SpotifyEntity {
    private String id;
    private String name;
    private String spotifyUrl;
}
