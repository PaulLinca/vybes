package com.vybes.service.vybe.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vybes.service.user.model.VybesUser;
import jakarta.persistence.*;
import lombok.*;

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
    private Set<VybesUser> fans = new HashSet<>();
}