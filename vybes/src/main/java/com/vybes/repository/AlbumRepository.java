package com.vybes.repository;

import com.vybes.model.Album;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findBySpotifyId(String spotifyId);
}
