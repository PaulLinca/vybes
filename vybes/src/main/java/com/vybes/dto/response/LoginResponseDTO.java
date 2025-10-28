package com.vybes.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LoginResponseDTO {
    private Long userId;
    private String email;
    private String username;
    private String profilePictureUrl;
    private Set<String> roles;
    private boolean requiresUsernameSetup;
}
