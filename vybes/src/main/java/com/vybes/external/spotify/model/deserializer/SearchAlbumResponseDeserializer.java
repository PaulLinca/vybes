package com.vybes.external.spotify.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.search.album.SearchAlbumResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchAlbumResponseDeserializer extends JsonDeserializer<SearchAlbumResponse> {

    @Override
    public SearchAlbumResponse deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode albumsNode = rootNode.get("albums");
        JsonNode itemsNode = albumsNode.get("items");

        List<SpotifyAlbum> albums = new ArrayList<>();

        for (JsonNode albumNode : itemsNode) {
            SpotifyAlbum album = new SpotifyAlbum();

            album.setId(albumNode.get("id").asText());
            album.setName(albumNode.get("name").asText());
            album.setSpotifyUrl(albumNode.get("external_urls").get("spotify").asText());

            if (albumNode.has("images") && albumNode.get("images").size() > 0) {
                album.setImageUrl(albumNode.get("images").get(0).get("url").asText());
            }

            album.setReleaseDate(albumNode.get("release_date").asText());

            List<SpotifyArtist> artists = new ArrayList<>();
            JsonNode artistsNode = albumNode.get("artists");

            for (JsonNode artistNode : artistsNode) {
                SpotifyArtist artist = new SpotifyArtist();
                artist.setId(artistNode.get("id").asText());
                artist.setName(artistNode.get("name").asText());
                artist.setSpotifyUrl(artistNode.get("external_urls").get("spotify").asText());

                artists.add(artist);
            }

            album.setArtists(artists);
            albums.add(album);
        }

        return SearchAlbumResponse.builder().searchAlbumItems(albums).build();
    }
}
