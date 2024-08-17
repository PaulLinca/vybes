package com.vybes.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private VybesUserResponseDTO user;
    private String jwt;
}
