package com.vybes.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private Long userId;
    private String email;
    private String username;
    private String jwt;
    private String refreshToken;
    private boolean requiresUsernameSetup;
}
