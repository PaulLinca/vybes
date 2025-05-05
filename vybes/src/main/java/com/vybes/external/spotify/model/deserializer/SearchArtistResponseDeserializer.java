package com.vybes.external.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.search.artist.SearchArtistResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchArtistResponseDeserializer extends JsonDeserializer<SearchArtistResponse> {

    @Override
    public SearchArtistResponse deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode itemsNode = rootNode.get("artists").get("items");
        List<SpotifyArtist> artists = new ArrayList<>();

        for (JsonNode artistNode : itemsNode) {
            SpotifyArtist artist = new SpotifyArtist();

            artist.setId(artistNode.get("id").asText());
            artist.setName(artistNode.get("name").asText());
            artist.setSpotifyUrl(artistNode.get("external_urls").get("spotify").asText());

            if (artistNode.has("images") && !artistNode.get("images").isEmpty()) {
                artist.setImageUrl(artistNode.get("images").get(0).get("url").asText());
            }

            artists.add(artist);
        }

        return SearchArtistResponse.builder().searchArtistItems(artists).build();
    }
}
