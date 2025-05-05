package com.vybes.service.vybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vybes.service.review.album.entity.AlbumReview;
import com.vybes.service.user.model.VybesUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(
        name = "`like`",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"vybe_id", "user_id"})})
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vybe_id")
    @JsonBackReference
    private Vybe vybe;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @JsonBackReference
    private Comment comment;

    @JoinColumn(name = "album_review_id")
    @JsonBackReference
    @ManyToOne
    private AlbumReview albumReview;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private VybesUser user;
}
