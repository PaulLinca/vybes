package com.vybes.external.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vybes.external.spotify.model.search.artist.SearchArtistResult;
import com.vybes.external.spotify.model.search.artist.SearchArtistResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchArtistResponseDeserializer extends JsonDeserializer<SearchArtistResponse> {

    private static final String NAME_KEY = "name";

    @Override
    public SearchArtistResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ArrayNode tracks = JsonNodeFactory.instance.arrayNode();

        for (JsonNode trackNode : valueAsJson.get("artists").get("items")) {
            tracks.add(getArtistNode(trackNode, factory.objectNode()));
        }

        List<SearchArtistResult> searchTrackItems =
                Arrays.asList(jsonParser.getCodec().treeToValue(tracks, SearchArtistResult[].class));

        return SearchArtistResponse.builder().searchArtistItems(searchTrackItems).build();
    }

    private ObjectNode getArtistNode(JsonNode trackNode, ObjectNode item) {
        item.putIfAbsent("id", trackNode.get("id"));
        item.putIfAbsent(NAME_KEY, trackNode.get(NAME_KEY));
        item.putIfAbsent("imageUrl", trackNode.get("images").get(0).get("url"));

        return item;
    }
}
