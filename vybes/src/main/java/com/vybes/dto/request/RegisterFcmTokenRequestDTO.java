package com.vybes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFcmTokenRequestDTO {
    @NotBlank
    @Size(max = 512)
    private String token;

    @Size(max = 40)
    private String devicePlatform;
}
