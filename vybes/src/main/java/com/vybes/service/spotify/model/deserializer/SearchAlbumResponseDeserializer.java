package com.vybes.service.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vybes.service.spotify.model.search.album.SearchAlbumItem;
import com.vybes.service.spotify.model.search.album.SearchAlbumResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchAlbumResponseDeserializer extends JsonDeserializer<SearchAlbumResponse> {

    private static final String NAME_KEY = "name";

    @Override
    public SearchAlbumResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ArrayNode tracks = JsonNodeFactory.instance.arrayNode();

        for (JsonNode trackNode : valueAsJson.get("albums").get("items")) {
            tracks.add(getAlbumNode(trackNode, factory.objectNode()));
        }

        List<SearchAlbumItem> searchAlbumItems =
                Arrays.asList(jsonParser.getCodec().treeToValue(tracks, SearchAlbumItem[].class));

        return SearchAlbumResponse.builder().searchAlbumItems(searchAlbumItems).build();
    }

    private ObjectNode getAlbumNode(JsonNode trackNode, ObjectNode item) {
        item.putIfAbsent("id", trackNode.get("id"));
        item.putIfAbsent(NAME_KEY, trackNode.get(NAME_KEY));
        item.putIfAbsent("imageUrl", trackNode.get("images").get(0).get("url"));

        return item;
    }
}
