package com.vybes.service.model.deserializer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vybes.service.model.Album;
import com.vybes.service.model.Artist;
import com.vybes.service.model.Track;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrackDeserializerTest {

    private static final String trackJson =
            "{\n"
                    + "    \"album\": {\n"
                    + "        \"artists\": [\n"
                    + "            {\n"
                    + "                \"external_urls\": {\n"
                    + "                    \"spotify\": \"https://open.spotify.com/artist/6U3ybJ9UHNKEdsH7ktGBZ7\"\n"
                    + "                },\n"
                    + "                \"href\": \"https://api.spotify.com/v1/artists/6U3ybJ9UHNKEdsH7ktGBZ7\",\n"
                    + "                \"id\": \"6U3ybJ9UHNKEdsH7ktGBZ7\",\n"
                    + "                \"name\": \"JID\",\n"
                    + "                \"type\": \"artist\",\n"
                    + "                \"uri\": \"spotify:artist:6U3ybJ9UHNKEdsH7ktGBZ7\"\n"
                    + "            }\n"
                    + "        ],\n"
                    + "        \"external_urls\": {\n"
                    + "            \"spotify\": \"https://open.spotify.com/album/29jlK0pu6Zv0TznE4uwpbq\"\n"
                    + "        },\n"
                    + "        \"href\": \"https://api.spotify.com/v1/albums/29jlK0pu6Zv0TznE4uwpbq\",\n"
                    + "        \"id\": \"29jlK0pu6Zv0TznE4uwpbq\",\n"
                    + "        \"images\": [\n"
                    + "            {\n"
                    + "                \"height\": 640,\n"
                    + "                \"url\": \"https://i.scdn.co/image/ab67616d0000b273b3577183ca1363cbf1379216\",\n"
                    + "                \"width\": 640\n"
                    + "            },\n"
                    + "            {\n"
                    + "                \"height\": 300,\n"
                    + "                \"url\": \"https://i.scdn.co/image/ab67616d00001e02b3577183ca1363cbf1379216\",\n"
                    + "                \"width\": 300\n"
                    + "            },\n"
                    + "            {\n"
                    + "                \"height\": 64,\n"
                    + "                \"url\": \"https://i.scdn.co/image/ab67616d00004851b3577183ca1363cbf1379216\",\n"
                    + "                \"width\": 64\n"
                    + "            }\n"
                    + "        ],\n"
                    + "        \"name\": \"The Forever Story (Extended Version)\",\n"
                    + "        \"release_date\": \"2022-10-31\",\n"
                    + "        \"release_date_precision\": \"day\",\n"
                    + "        \"total_tracks\": 16,\n"
                    + "        \"type\": \"album\",\n"
                    + "        \"uri\": \"spotify:album:29jlK0pu6Zv0TznE4uwpbq\"\n"
                    + "    },\n"
                    + "    \"artists\": [\n"
                    + "        {\n"
                    + "            \"external_urls\": {\n"
                    + "                \"spotify\": \"https://open.spotify.com/artist/6U3ybJ9UHNKEdsH7ktGBZ7\"\n"
                    + "            },\n"
                    + "            \"href\": \"https://api.spotify.com/v1/artists/6U3ybJ9UHNKEdsH7ktGBZ7\",\n"
                    + "            \"id\": \"6U3ybJ9UHNKEdsH7ktGBZ7\",\n"
                    + "            \"name\": \"JID\",\n"
                    + "            \"type\": \"artist\",\n"
                    + "            \"uri\": \"spotify:artist:6U3ybJ9UHNKEdsH7ktGBZ7\"\n"
                    + "        }\n"
                    + "    ],\n"
                    + "    \"disc_number\": 1,\n"
                    + "    \"duration_ms\": 224629,\n"
                    + "    \"explicit\": false,\n"
                    + "    \"external_ids\": {\n"
                    + "        \"isrc\": \"USUM72214817\"\n"
                    + "    },\n"
                    + "    \"external_urls\": {\n"
                    + "        \"spotify\": \"https://open.spotify.com/track/4BLIa4mBW1u5d9PLoMoENs\"\n"
                    + "    },\n"
                    + "    \"href\": \"https://api.spotify.com/v1/tracks/4BLIa4mBW1u5d9PLoMoENs\",\n"
                    + "    \"id\": \"4BLIa4mBW1u5d9PLoMoENs\",\n"
                    + "    \"is_local\": false,\n"
                    + "    \"name\": \"Kody Blu 31\",\n"
                    + "    \"popularity\": 26,\n"
                    + "    \"preview_url\": null,\n"
                    + "    \"track_number\": 7,\n"
                    + "    \"type\": \"track\",\n"
                    + "    \"uri\": \"spotify:track:4BLIa4mBW1u5d9PLoMoENs\"\n"
                    + "}";
    private final TrackDeserializer testee = new TrackDeserializer();

    @Spy private ObjectMapper objectMapper = new ObjectMapper();
    @Mock private JsonParser jsonParser;

    @Test
    @SneakyThrows
    void deserializeResponse() {
        when(jsonParser.getCodec()).thenReturn(objectMapper);
        JsonNode trackNode = objectMapper.readTree(trackJson);
        when(objectMapper.readTree(jsonParser)).thenReturn(trackNode);

        Track response = testee.deserialize(jsonParser, null);

        assertThat(response.getId(), is("4BLIa4mBW1u5d9PLoMoENs"));
        assertThat(response.getName(), is("Kody Blu 31"));
        assertThat(
                response.getSpotifyUrl(),
                is("https://open.spotify.com/track/4BLIa4mBW1u5d9PLoMoENs"));

        Album album = response.getAlbum();
        assertThat(album.getId(), is("29jlK0pu6Zv0TznE4uwpbq"));
        assertThat(album.getName(), is("The Forever Story (Extended Version)"));
        assertThat(
                album.getSpotifyUrl(), is("https://open.spotify.com/album/29jlK0pu6Zv0TznE4uwpbq"));

        Artist artist = response.getArtists().get(0);
        assertThat(artist.getId(), is("6U3ybJ9UHNKEdsH7ktGBZ7"));
        assertThat(artist.getName(), is("JID"));
        assertThat(
                artist.getSpotifyUrl(),
                is("https://open.spotify.com/artist/6U3ybJ9UHNKEdsH7ktGBZ7"));
    }
}
