package com.vybes.external.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.ArtistDeserializer;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonDeserialize(using = ArtistDeserializer.class)
@ToString
public class SpotifyArtist extends SpotifyEntity {
    private String spotifyUrl;
    private String imageUrl;
}
