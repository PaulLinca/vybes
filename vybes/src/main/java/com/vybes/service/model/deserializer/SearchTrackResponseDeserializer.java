package com.vybes.service.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vybes.service.model.search.SearchTrackItem;
import com.vybes.service.model.search.SearchTrackResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SearchTrackResponseDeserializer extends JsonDeserializer<SearchTrackResponse> {

    private static final String ALBUM = "album";
    private static final String NAME = "name";

    @Override
    public SearchTrackResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);
        Iterator<JsonNode> iterator = valueAsJson.get("tracks").get("items").iterator();
        ArrayNode jsonArray = JsonNodeFactory.instance.arrayNode();
        while (iterator.hasNext()) {
            JsonNode trackNode = iterator.next();
            JsonNodeFactory factory = JsonNodeFactory.instance;

            ObjectNode objectNode = factory.objectNode();
            objectNode.putIfAbsent("id", trackNode.get("id"));
            objectNode.putIfAbsent(NAME, trackNode.get(NAME));
            objectNode.putIfAbsent(ALBUM, trackNode.get(ALBUM).get(NAME));
            objectNode.putIfAbsent("artist", trackNode.get("artists").get(0).get(NAME));
            objectNode.putIfAbsent(
                    "imageUrl", trackNode.get(ALBUM).get("images").get(0).get("url"));

            jsonArray.add(objectNode);
        }
        List<SearchTrackItem> searchTrackItems =
                Arrays.asList(new ObjectMapper().readValue(jsonArray.toString(), SearchTrackItem[].class));

        return SearchTrackResponse.builder().searchTrackItems(searchTrackItems).build();
    }
}
