package com.vybes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class PostDTO {
    private String type;
    private Object content;
    private ZonedDateTime postedDate;
}
