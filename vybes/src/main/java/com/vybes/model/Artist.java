package com.vybes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "artists",
        indexes = {
            @Index(name = "idx_artist_spotify_id", columnList = "spotifyId"),
            @Index(name = "idx_artist_name", columnList = "name")
        })
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String spotifyId;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @CreationTimestamp private LocalDateTime createdAt;

    @UpdateTimestamp private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "artists")
    @JsonIgnore
    private Set<Album> albums = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteArtists")
    @JsonIgnore
    private Set<VybesUser> favoritedBy = new HashSet<>();
}
