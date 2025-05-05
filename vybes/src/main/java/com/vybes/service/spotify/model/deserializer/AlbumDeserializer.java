package com.vybes.service.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.spotify.model.entity.SpotifyArtist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDeserializer extends JsonDeserializer<SpotifyAlbum> {

    @Override
    public SpotifyAlbum deserialize(
            final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {

        JsonNode valueAsJson = jsonParser.getCodec().readTree(jsonParser);

        return SpotifyAlbum.builder()
                .id(valueAsJson.get("id").textValue())
                .name(valueAsJson.get("name").textValue())
                .spotifyUrl(valueAsJson.get("external_urls").get("spotify").textValue())
                .imageUrl(valueAsJson.get("images").get(0).get("url").textValue())
                .releaseDate(valueAsJson.get("release_date").textValue())
                .artists(getArtists(valueAsJson.withArray("artists")))
                .build();
    }

    private List<SpotifyArtist> getArtists(JsonNode artistsArrayNode) {
        List<SpotifyArtist> artists = new ArrayList<>();

        for (JsonNode artistNode : artistsArrayNode) {
            SpotifyArtist artist =
                    SpotifyArtist.builder()
                            .id(artistNode.get("id").asText())
                            .name(artistNode.get("name").asText())
                            .spotifyUrl(artistNode.get("external_urls").get("spotify").asText())
                            .build();

            artists.add(artist);
        }

        return artists;
    }
}
