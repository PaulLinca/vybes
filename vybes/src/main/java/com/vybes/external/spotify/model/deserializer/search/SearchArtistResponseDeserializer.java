package com.vybes.external.spotify.model.deserializer.search;

import static com.vybes.external.spotify.model.deserializer.DeserializerHelper.getArtists;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.external.spotify.model.entity.search.SearchArtistResponse;

import java.io.IOException;

public class SearchArtistResponseDeserializer extends JsonDeserializer<SearchArtistResponse> {

    @Override
    public SearchArtistResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        JsonNode itemsNode = rootNode.get("artists").get("items");

        return SearchArtistResponse.builder().searchArtistItems(getArtists(itemsNode)).build();
    }
}
