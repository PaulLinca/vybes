package com.vybes.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private Long postId;
    private UserDTO user;
    private List<CommentLikeDTO> likes;
}
