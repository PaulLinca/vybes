package com.vybes.external.spotify.model.deserializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeserializerHelper {

    public static SpotifyArtist getArtist(JsonNode artistNode) {
        return SpotifyArtist.builder()
                .id(artistNode.get("id").textValue())
                .name(artistNode.get("name").textValue())
                .spotifyUrl(artistNode.get("external_urls").at("/spotify").textValue())
                .imageUrl(
                        Optional.ofNullable(artistNode.get("images"))
                                .map(ArrayNode.class::cast)
                                .map(n -> n.get(0))
                                .map(n -> n.get("url"))
                                .map(JsonNode::textValue)
                                .orElse(null))
                .build();
    }

    public static List<SpotifyArtist> getArtists(JsonNode artistsNode) {
        List<SpotifyArtist> artists = new ArrayList<>();
        for (JsonNode artistNode : artistsNode) {
            artists.add(getArtist(artistNode));
        }
        return artists;
    }

    public static SpotifyAlbum getAlbum(JsonNode albumNode) {
        return SpotifyAlbum.builder()
                .id(albumNode.get("id").textValue())
                .name(albumNode.get("name").textValue())
                .spotifyUrl(albumNode.get("external_urls").get("spotify").textValue())
                .imageUrl(getImageUrl(albumNode))
                .releaseDate(albumNode.get("release_date").textValue())
                .build();
    }

    public static SpotifyTrack getTrack(JsonNode trackNode) {
        SpotifyTrack track = new SpotifyTrack();
        track.setId(trackNode.get("id").asText());
        track.setName(trackNode.get("name").asText());
        track.setSpotifyUrl(trackNode.get("external_urls").get("spotify").asText());
        track.setArtists(getArtists(trackNode.get("artists")));

        JsonNode albumNode = trackNode.get("album");
        track.setAlbum(getAlbum(albumNode));

        track.setImageUrl(
                Optional.ofNullable(albumNode.get("images"))
                        .map(n -> n.get(0))
                        .map(n -> n.get("url"))
                        .map(JsonNode::asText)
                        .orElse(null));

        return track;
    }

    private static String getImageUrl(JsonNode albumNode){
        if (albumNode.has("images") && !albumNode.get("images").isEmpty()) {
            return albumNode.get("images").get(0).get("url").asText();
        }
        return null;
    }
}
