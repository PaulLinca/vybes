package com.vybes.service.client;

import com.vybes.service.model.AuthorizationTokenResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

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

    public void setAccessToken() {
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

    public Object searchTrack() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> result =
                restTemplate.exchange(
                        BASE_URL + "/search?type=track&query=track=Kody Blu&limit=2",
                        HttpMethod.GET,
                        entity,
                        Object.class);

        return result.getBody();
    }
}
