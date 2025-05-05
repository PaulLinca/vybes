package com.vybes.external.spotify.model.deserializer;

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
            SpotifyTrack track = new SpotifyTrack();

            track.setId(trackNode.get("id").asText());
            track.setName(trackNode.get("name").asText());
            track.setSpotifyUrl(trackNode.get("external_urls").get("spotify").asText());

            List<SpotifyArtist> artists = new ArrayList<>();
            JsonNode artistsNode = trackNode.get("artists");

            for (JsonNode artistNode : artistsNode) {
                SpotifyArtist artist = new SpotifyArtist();
                artist.setId(artistNode.get("id").asText());
                artist.setName(artistNode.get("name").asText());
                artist.setSpotifyUrl(artistNode.get("external_urls").get("spotify").asText());

                artists.add(artist);
            }

            track.setArtists(artists);

            JsonNode albumNode = trackNode.get("album");
            SpotifyAlbum album =
                    SpotifyAlbum.builder()
                            .id(albumNode.get("id").asText())
                            .name(albumNode.get("name").asText())
                            .spotifyUrl(albumNode.get("external_urls").get("spotify").asText())
                            .build();

            track.setAlbum(album);
            track.setImageUrl(
                    Optional.ofNullable(albumNode.get("images"))
                            .map(n -> n.get(0))
                            .map(n -> n.get("url"))
                            .map(JsonNode::asText)
                            .orElse(null));

            tracks.add(track);
        }

        return SearchTrackResponse.builder().searchTrackItems(tracks).build();
    }
}
