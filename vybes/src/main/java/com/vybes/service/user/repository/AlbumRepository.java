package com.vybes.service.user.repository;

import com.vybes.service.user.model.Album;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findBySpotifyId(String spotifyId);
}
