package com.vybes.service.spotify.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.spotify.model.SpotifyEntity;
import com.vybes.service.spotify.model.deserializer.TrackDeserializer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@JsonDeserialize(using = TrackDeserializer.class)
public class Track extends SpotifyEntity {
    private Album album;
    private List<Artist> artists;
}
