package com.vybes.dto.response;

import java.util.Set;
import com.vybes.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private Long userId;
    private String email;
    private String username;
    private Set<Role> roles;
    private boolean requiresUsernameSetup;
}
