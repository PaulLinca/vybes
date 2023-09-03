package com.vybes.service.model;

import lombok.Data;

@Data
public class AuthorizationTokenResponse {
    private String accessToken;
    private String tokenType;
}
