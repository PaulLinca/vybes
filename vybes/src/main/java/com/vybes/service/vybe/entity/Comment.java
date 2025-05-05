package com.vybes.service.vybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vybes.service.review.album.entity.AlbumReview;
import com.vybes.service.user.model.VybesUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "vybe_id")
    @JsonBackReference
    private Vybe vybe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private VybesUser user;

    @JsonManagedReference
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "album_review_id")
    private AlbumReview albumReview;

    private ZonedDateTime timestamp;
}
