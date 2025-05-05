package com.vybes.external.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.TrackDeserializer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonDeserialize(using = TrackDeserializer.class)
public class SpotifyTrack extends SpotifyEntity {
    private SpotifyAlbum album;
    private List<SpotifyArtist> artists;
}
