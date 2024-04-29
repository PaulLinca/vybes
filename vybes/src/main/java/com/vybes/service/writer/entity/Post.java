package com.vybes.service.writer.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
    private int id;
    private String songName;
}
