package com.vybes.service.spotify.client;

import com.vybes.service.spotify.model.AuthorizationTokenResponse;
import com.vybes.service.spotify.model.entity.Album;
import com.vybes.service.spotify.model.entity.Artist;
import com.vybes.service.spotify.model.entity.Track;
import com.vybes.service.spotify.model.search.SearchTrackItem;
import com.vybes.service.spotify.model.search.SearchTrackResponse;

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

    public List<SearchTrackItem> searchTrack(String searchString) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        String uri =
                UriComponentsBuilder.fromHttpUrl(BASE_URL + "/search")
                        .queryParam("type", "track")
                        .queryParam("limit", "4")
                        .queryParam("query", searchString)
                        .encode()
                        .toUriString();
        ResponseEntity<SearchTrackResponse> result =
                restTemplate.exchange(
                        uri, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});

        return result.getBody().getSearchTrackItems();
    }

    public Track getTrack(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Track> result =
                restTemplate.exchange(
                        BASE_URL + "/tracks/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {});

        return result.getBody();
    }

    public Album getAlbum(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Album> result =
                restTemplate.exchange(
                        BASE_URL + "/albums/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {});

        return result.getBody();
    }

    public Artist getArtist(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Artist> result =
                restTemplate.exchange(
                        BASE_URL + "/artists/" + id,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<>() {});

        return result.getBody();
    }
}
