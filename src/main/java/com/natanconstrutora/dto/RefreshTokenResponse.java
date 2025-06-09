package com.natanconstrutora.dto;

import lombok.Data;

@Data
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
} 