package com.vybes.external.spotify.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public abstract class SpotifyEntity {
    private String id;
    private String name;
    private String spotifyUrl;
}
