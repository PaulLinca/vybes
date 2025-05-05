package com.vybes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String spotifyId;

    private String name;

    private String imageUrl;

    @ManyToMany(mappedBy = "spotifyArtists")
    @JsonIgnore
    private List<Vybe> vybes;

    @ManyToMany(mappedBy = "favoriteArtists")
    @JsonIgnore
    private Set<VybesUser> favoritedBy = new HashSet<>();
}
