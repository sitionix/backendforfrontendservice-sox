package com.app_afesox.athssox.client.dto;

import lombok.Data;

@Data
public class RefreshAccessTokenRequestDTO {

    private String refreshToken;

    private String sessionSourceId;

    public RefreshAccessTokenRequestDTO refreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public RefreshAccessTokenRequestDTO sessionSourceId(final String sessionSourceId) {
        this.sessionSourceId = sessionSourceId;
        return this;
    }
}
