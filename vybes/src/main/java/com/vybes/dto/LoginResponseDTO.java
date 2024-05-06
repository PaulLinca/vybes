package com.vybes.dto;

import com.vybes.service.user.model.VybesUser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private VybesUser user;
    private String jwt;
}
