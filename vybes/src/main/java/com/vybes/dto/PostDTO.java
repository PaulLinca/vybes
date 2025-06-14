package com.vybes.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = VybeDTO.class, name = "VYBE"),
    @JsonSubTypes.Type(value = AlbumReviewDTO.class, name = "ALBUM_REVIEW")
})
public interface PostDTO {}
