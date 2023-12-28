package com.vybes.service.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vybes.service.model.SearchTrackResponse;
import com.vybes.service.model.Track;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchTrackResponseDeserializer extends JsonDeserializer<SearchTrackResponse> {

    @Override
    public SearchTrackResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);
        JsonNode tracksArrayNode = valueAsJson.get("tracks").get("items");
        System.out.println(tracksArrayNode.toString());
        List<Track> tracks =
                Arrays.asList(
                        new ObjectMapper().readValue(tracksArrayNode.toString(), Track[].class));

        return SearchTrackResponse.builder().tracks(tracks).build();
    }
}
