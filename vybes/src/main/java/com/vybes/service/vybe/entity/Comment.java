package com.vybes.service.vybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vybes.service.user.model.VybesUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;

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
}
