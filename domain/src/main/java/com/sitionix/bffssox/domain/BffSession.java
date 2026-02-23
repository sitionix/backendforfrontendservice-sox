package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class BffSession {

    private String userId;

    private String email;

    private String role;

    private UUID siteId;

    private String authSessionSourceId;

    private String accessToken;

    private Instant accessTokenExpiresAt;

    private String refreshToken;

    private Instant refreshTokenExpiresAt;

    private String csrfToken;

    private Instant createdAt;

    private Instant lastUsedAt;

    private Instant lastRotatedAt;

    private Instant maxExpiresAt;
}
