package com.vybes.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("ALBUM_REVIEW")
public class AlbumReview extends Post {
    private String albumName;
    private String spotifyId;
    private Integer score;
    private LocalDate releaseDate;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "album_review_artist",
            joinColumns = @JoinColumn(name = "album_review_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> spotifyArtists;

    @JsonManagedReference
    @OneToMany(mappedBy = "albumReview", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackReview> trackReviews;
}
