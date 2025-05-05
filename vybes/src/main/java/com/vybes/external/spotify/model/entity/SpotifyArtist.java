package com.vybes.external.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.ArtistDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@JsonDeserialize(using = ArtistDeserializer.class)
@NoArgsConstructor
@ToString(callSuper = true)
public class SpotifyArtist extends SpotifyEntity {
    private String spotifyUrl;
    private String imageUrl;
}
