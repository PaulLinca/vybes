package com.vybes.service.vybe.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class CommentDTO {
    private Long id;
    private String text;
    private Long vybeId;
    private UserDTO user;
    private ZonedDateTime timestamp;
    private List<Long> likeIds;
}
