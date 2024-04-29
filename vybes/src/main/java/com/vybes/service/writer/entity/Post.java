package com.vybes.service.writer.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String songName;
}
