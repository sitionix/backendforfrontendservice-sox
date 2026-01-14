package com.app_afesox.athssox.client.dto;

import lombok.Data;

@Data
public class RefreshAccessTokenResponseDTO {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;

    public RefreshAccessTokenResponseDTO accessToken(final String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public RefreshAccessTokenResponseDTO refreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public RefreshAccessTokenResponseDTO tokenType(final String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public RefreshAccessTokenResponseDTO expiresIn(final Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }
}
