package com.vybes.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class VybesUserResponseDTO {
    private Long id;
    private String username;
}
