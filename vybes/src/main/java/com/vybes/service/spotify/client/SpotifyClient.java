package com.vybes.service.spotify.client;

import com.vybes.service.spotify.model.AuthorizationTokenResponse;
import com.vybes.service.spotify.model.entity.SpotifyAlbum;
import com.vybes.service.spotify.model.entity.SpotifyArtist;
import com.vybes.service.spotify.model.entity.SpotifyTrack;
import com.vybes.service.spotify.model.search.album.SearchAlbumItem;
import com.vybes.service.spotify.model.search.album.SearchAlbumResponse;
import com.vybes.service.spotify.model.search.artist.SearchArtistItem;
import com.vybes.service.spotify.model.search.artist.SearchArtistResponse;
import com.vybes.service.spotify.model.search.track.SearchTrackItem;
import com.vybes.service.spotify.model.search.track.SearchTrackResponse;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class SpotifyClient {

    private static final String BASE_URL = "https://api.spotify.com/v1";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private final WebClient.Builder webClientBuilder;
    private final AtomicReference<String> tokenRef = new AtomicReference<>();
    @Value("${spotify.http.token.client-id}")
    private String clientId;
    @Value("${spotify.http.token.client-secret}")
    private String clientSecret;
    private WebClient spotifyWebClient;

    @PostConstruct
    public void initialize() {
        refreshAccessToken();
        this.spotifyWebClient = createBaseWebClient();
    }

    private WebClient createBaseWebClient() {
        return webClientBuilder
                .baseUrl(BASE_URL)
                .filter(this::tokenFilter)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private Mono<ClientResponse> tokenFilter(ClientRequest request, ExchangeFunction next) {
        ClientRequest authorizedRequest = ClientRequest.from(request)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRef.get())
                .build();

        return next.exchange(authorizedRequest)
                .flatMap(response -> {
                    if (response.statusCode() == HttpStatus.UNAUTHORIZED) {
                        return Mono.fromCallable(() -> {
                                    refreshAccessToken();
                                    return true;
                                })
                                .then(Mono.defer(() -> {
                                    ClientRequest newRequest = ClientRequest.from(request)
                                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRef.get())
                                            .build();
                                    return next.exchange(newRequest);
                                }));
                    }
                    return Mono.just(response);
                });
    }

    public void refreshAccessToken() {
        AuthorizationTokenResponse tokenResponse = getToken();
        tokenRef.set(tokenResponse.getAccessToken());
    }

    public AuthorizationTokenResponse getToken() {
        WebClient webClient = webClientBuilder.build();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        return webClient
                .post()
                .uri(TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(AuthorizationTokenResponse.class)
                .block();
    }

    public List<SearchTrackItem> searchTrack(String searchString) {
        return spotifyWebClient
                .get()
                .uri(uriBuilder -> buildSearchUri(uriBuilder, searchString, "track"))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SearchTrackResponse>() {})
                .map(SearchTrackResponse::getSearchTrackItems)
                .block();
    }

    public List<SearchArtistItem> searchArtist(String searchString) {
        return spotifyWebClient
                .get()
                .uri(uriBuilder -> buildSearchUri(uriBuilder, searchString, "artist"))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SearchArtistResponse>() {})
                .map(SearchArtistResponse::getSearchArtistItems)
                .block();
    }

    public List<SearchAlbumItem> searchAlbum(String searchString) {
        return spotifyWebClient
                .get()
                .uri(uriBuilder -> buildSearchUri(uriBuilder, searchString, "album"))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SearchAlbumResponse>() {})
                .map(SearchAlbumResponse::getSearchAlbumItems)
                .block();
    }

    public SpotifyTrack getTrack(String id) {
        return spotifyWebClient
                .get()
                .uri("/tracks/{id}", id)
                .retrieve()
                .bodyToMono(SpotifyTrack.class)
                .block();
    }

    public SpotifyAlbum getAlbum(String id) {
        return spotifyWebClient
                .get()
                .uri("/albums/{id}", id)
                .retrieve()
                .bodyToMono(SpotifyAlbum.class)
                .block();
    }

    public SpotifyArtist getArtist(String id) {
        return spotifyWebClient
                .get()
                .uri("/artists/{id}", id)
                .retrieve()
                .bodyToMono(SpotifyArtist.class)
                .block();
    }

    private URI buildSearchUri(UriBuilder uriBuilder, String searchString, String type) {
        return uriBuilder
                .path("/search")
                .queryParam("type", type)
                .queryParam("limit", "10")
                .queryParam("market", "US")
                .queryParam("q", searchString)
                .build();
    }
}