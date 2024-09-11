package com.vybes.service.vybe.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private Long vybeId;
    private Long userId;
    private List<Long> likeIds;
}
