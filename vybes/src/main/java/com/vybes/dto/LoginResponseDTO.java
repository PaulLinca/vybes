package com.vybes.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private Long userId;
    private String username;
    private String jwt;
}
