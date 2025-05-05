package com.vybes.repository;

import com.vybes.model.Artist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findBySpotifyId(String spotifyId);
}
