package com.vybes.service.user.repository;

import com.vybes.service.vybe.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findBySpotifyId(String spotifyId);
}
