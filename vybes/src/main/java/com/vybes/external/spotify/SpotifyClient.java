package com.vybes.external.spotify;

import com.vybes.external.spotify.model.AuthorizationTokenResponse;
import com.vybes.external.spotify.model.entity.SpotifyAlbum;
import com.vybes.external.spotify.model.entity.SpotifyArtist;
import com.vybes.external.spotify.model.entity.SpotifyTrack;
import com.vybes.external.spotify.model.entity.search.SearchAlbumResponse;
import com.vybes.external.spotify.model.entity.search.SearchArtistResponse;
import com.vybes.external.spotify.model.entity.search.SearchTrackResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpotifyClient {

    private static final String BASE_URL = "https://api.spotify.com/v1";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    @Value("${spotify.http.token.client-id}")
    private String clientId;

    @Value("${spotify.http.token.client-secret}")
    private String clientSecret;

    private String token;

    public void refreshAccessToken() {
        token = getToken().getAccessToken();
    }

    public AuthorizationTokenResponse getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthorizationTokenResponse> response =
                restTemplate.exchange(
                        TOKEN_URL, HttpMethod.POST, entity, AuthorizationTokenResponse.class);

        return response.getBody();
    }

    public List<SpotifyTrack> searchTrack(String searchString) {
        ResponseEntity<SearchTrackResponse> result =
                new RestTemplate()
                        .exchange(
                                buildSearchUri(searchString, "track"),
                                HttpMethod.GET,
                                buildHttpEntity(),
                                new ParameterizedTypeReference<>() {});

        return result.getBody().getSearchTrackItems();
    }

    public List<SpotifyArtist> searchArtist(String searchString) {
        ResponseEntity<SearchArtistResponse> result =
                new RestTemplate()
                        .exchange(
                                buildSearchUri(searchString, "artist"),
                                HttpMethod.GET,
                                buildHttpEntity(),
                                new ParameterizedTypeReference<>() {});

        return result.getBody().getSearchArtistItems();
    }

    public List<SpotifyAlbum> searchAlbum(String searchString) {
        ResponseEntity<SearchAlbumResponse> result =
                new RestTemplate()
                        .exchange(
                                buildSearchUri(searchString, "album"),
                                HttpMethod.GET,
                                buildHttpEntity(),
                                new ParameterizedTypeReference<>() {});

        return result.getBody().getSearchAlbumItems();
    }

    public SpotifyTrack getTrack(String id) {
        ResponseEntity<SpotifyTrack> result =
                new RestTemplate()
                        .exchange(
                                BASE_URL + "/tracks/" + id,
                                HttpMethod.GET,
                                buildHttpEntity(),
                                new ParameterizedTypeReference<>() {});

        return result.getBody();
    }

    public SpotifyAlbum getAlbum(String id) {
        ResponseEntity<SpotifyAlbum> result =
                new RestTemplate()
                        .exchange(
                                BASE_URL + "/albums/" + id,
                                HttpMethod.GET,
                                buildHttpEntity(),
                                new ParameterizedTypeReference<>() {});

        return result.getBody();
    }

    public SpotifyArtist getArtist(String id) {
        ResponseEntity<SpotifyArtist> result =
                new RestTemplate()
                        .exchange(
                                BASE_URL + "/artists/" + id,
                                HttpMethod.GET,
                                buildHttpEntity(),
                                new ParameterizedTypeReference<>() {});

        return result.getBody();
    }

    private String buildSearchUri(String searchString, String type) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL + "/search")
                .queryParam("type", type)
                .queryParam("limit", "10")
                .queryParam("market", "US")
                .queryParam("query", searchString)
                .encode()
                .toUriString();
    }

    private HttpEntity<String> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        return new HttpEntity<>(null, headers);
    }
}
