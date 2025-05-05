package com.vybes.dto;

import lombok.Data;

@Data
public class CommentLikeDTO {
    private Long id;
    private Long commentId;
    private Long userId;
}
