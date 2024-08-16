package com.vybes.service.vybe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vybes.service.user.model.VybesUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "`like`")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vybe_id")
    @JsonBackReference
    private Vybe vybe;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private VybesUser user;
}
