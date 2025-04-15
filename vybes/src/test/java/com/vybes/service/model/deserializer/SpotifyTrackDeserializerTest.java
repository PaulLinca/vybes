package com.vybes.service.model.deserializer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vybes.service.spotify.model.deserializer.TrackDeserializer;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.spotify.model.entity.SpotifyArtist;
import com.vybes.service.spotify.model.entity.SpotifyTrack;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@ExtendWith(MockitoExtension.class)
class SpotifyTrackDeserializerTest {

    private final TrackDeserializer testee = new TrackDeserializer();

    @Spy private ObjectMapper objectMapper = new ObjectMapper();
    @Mock private JsonParser jsonParser;

    @Test
    @SneakyThrows
    void deserializeResponse() {
        when(jsonParser.getCodec()).thenReturn(objectMapper);
        JsonNode trackNode = objectMapper.readTree(readFileAsString("input/getTrackResponse.json"));
        when(objectMapper.readTree(jsonParser)).thenReturn(trackNode);

        SpotifyTrack response = testee.deserialize(jsonParser, null);

        assertThat(response.getId(), is("4BLIa4mBW1u5d9PLoMoENs"));
        assertThat(response.getName(), is("Kody Blu 31"));
        assertThat(
                response.getSpotifyUrl(),
                is("https://open.spotify.com/track/4BLIa4mBW1u5d9PLoMoENs"));

        SpotifyAlbum spotifyAlbum = response.getAlbum();
        assertThat(spotifyAlbum.getId(), is("29jlK0pu6Zv0TznE4uwpbq"));
        assertThat(spotifyAlbum.getName(), is("The Forever Story (Extended Version)"));
        assertThat(
                spotifyAlbum.getSpotifyUrl(),
                is("https://open.spotify.com/album/29jlK0pu6Zv0TznE4uwpbq"));

        SpotifyArtist artist = response.getArtists().get(0);
        assertThat(artist.getId(), is("6U3ybJ9UHNKEdsH7ktGBZ7"));
        assertThat(artist.getName(), is("JID"));
        assertThat(
                artist.getSpotifyUrl(),
                is("https://open.spotify.com/artist/6U3ybJ9UHNKEdsH7ktGBZ7"));
    }

    private String readFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        StringBuilder resultStringBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }

        return resultStringBuilder.toString();
    }
}
