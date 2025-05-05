package com.vybes.external.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vybes.external.spotify.model.search.track.SearchTrackResult;
import com.vybes.external.spotify.model.search.track.SearchTrackResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchTrackResponseDeserializer extends JsonDeserializer<SearchTrackResponse> {

    private static final String NAME_KEY = "name";
    private static final String ALBUM_KEY = "album";

    @Override
    public SearchTrackResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ArrayNode tracks = JsonNodeFactory.instance.arrayNode();

        for (JsonNode trackNode : valueAsJson.get("tracks").get("items")) {
            tracks.add(getTrackNode(trackNode, factory.objectNode()));
        }

        List<SearchTrackResult> searchTrackResults =
                Arrays.asList(jsonParser.getCodec().treeToValue(tracks, SearchTrackResult[].class));

        return SearchTrackResponse.builder().searchTrackItems(searchTrackResults).build();
    }

    private ObjectNode getTrackNode(JsonNode trackNode, ObjectNode trackItem) {
        trackItem.putIfAbsent("id", trackNode.get("id"));
        trackItem.putIfAbsent(NAME_KEY, trackNode.get(NAME_KEY));
        trackItem.putIfAbsent(ALBUM_KEY, trackNode.get(ALBUM_KEY).get(NAME_KEY));
        trackItem.putIfAbsent("artist", trackNode.get("artists").get(0).get(NAME_KEY));
        trackItem.putIfAbsent("imageUrl", trackNode.get(ALBUM_KEY).get("images").get(0).get("url"));

        return trackItem;
    }
}
