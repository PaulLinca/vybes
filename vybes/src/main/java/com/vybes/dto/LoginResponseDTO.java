package com.vybes.dto;

import com.vybes.security.model.ApplicationUser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private ApplicationUser user;
    private String jwt;
}
