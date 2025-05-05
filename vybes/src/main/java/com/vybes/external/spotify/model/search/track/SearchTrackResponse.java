package com.vybes.external.spotify.model.search.track;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.external.spotify.model.deserializer.SearchTrackResponseDeserializer;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = SearchTrackResponseDeserializer.class)
public class SearchTrackResponse {
    private List<SearchTrackResult> searchTrackItems;
}
