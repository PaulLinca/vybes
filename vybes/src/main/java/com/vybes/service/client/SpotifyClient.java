package com.vybes.service.client;

import java.util.Collections;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SpotifyClient {
    private static final String BASE_URL = "https://api.spotify.com/v1";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    public Object searchTrack() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.add("Authorization", "Bearer BQAFWyOynpoGiDhwklbcSsAbkwcbNpGJUAeizZ2Nl6KNWFpqBUQFGuCsfv45kSuLcgOy8Jiv4eduucv6lAWWN2gzSTyN0YbwtYgk6kp6D_aFvhsyNL8");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<?> result =
                restTemplate.exchange(BASE_URL + "/search?type=track&query=track=Kody Blu&limit=2", HttpMethod.GET, entity, String.class);
        return result.getBody();
    }
}
