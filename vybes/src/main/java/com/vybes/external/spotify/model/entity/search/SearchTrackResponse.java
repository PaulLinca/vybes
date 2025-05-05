package com.vybes.external.spotify.model.entity.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.search.SearchTrackResponseDeserializer;
import com.vybes.external.spotify.model.entity.SpotifyTrack;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = SearchTrackResponseDeserializer.class)
public class SearchTrackResponse {
    private List<SpotifyTrack> searchTrackItems;
}
