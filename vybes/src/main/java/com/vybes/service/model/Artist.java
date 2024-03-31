package com.vybes.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    private String id;
    private String name;
    private String spotifyUrl;
}
