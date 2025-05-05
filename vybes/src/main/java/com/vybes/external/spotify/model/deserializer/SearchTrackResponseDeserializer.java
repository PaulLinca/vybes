package com.vybes.external.spotify.model.deserializer;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtist;
import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtists;
import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getTrack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.external.spotify.model.search.track.SearchTrackResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchTrackResponseDeserializer extends JsonDeserializer<SearchTrackResponse> {

    @Override
    public SearchTrackResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode itemsNode = rootNode.get("tracks").get("items");

        List<SpotifyTrack> tracks = new ArrayList<>();
        for (JsonNode trackNode : itemsNode) {
            tracks.add(getTrack(trackNode));
        }

        return SearchTrackResponse.builder().searchTrackItems(tracks).build();
    }
}
