package com.vybes.external.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.TrackDeserializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@JsonDeserialize(using = TrackDeserializer.class)
@NoArgsConstructor
public class SpotifyTrack extends SpotifyEntity {
    private String imageUrl;
    private SpotifyAlbum album;
    private List<SpotifyArtist> artists;
}
