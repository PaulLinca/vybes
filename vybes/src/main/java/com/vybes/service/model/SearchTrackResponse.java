package com.vybes.service.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vybes.service.model.deserializer.SearchTrackResponseDeserializer;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonDeserialize(using = SearchTrackResponseDeserializer.class)
public class SearchTrackResponse {

    private List<Track> tracks;
}
