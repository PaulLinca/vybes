package com.vybes.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FavoritesUpdateRequest {
    private List<String> artistIds;
    private List<String> albumIds;
}