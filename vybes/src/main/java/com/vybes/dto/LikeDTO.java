package com.vybes.dto;

import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private Long vybeId;
    private Long userId;
    private Long commentId;
}
