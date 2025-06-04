package com.vybes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TrackReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String spotifyTrackId;
    private boolean isFavorite;

    @Enumerated(EnumType.STRING)
    private TrackRating rating;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "album_review_id", nullable = false)
    private AlbumReview albumReview;

    public enum TrackRating {
        AWFUL, MEH, OKAY, GREAT, AMAZING
    }
}