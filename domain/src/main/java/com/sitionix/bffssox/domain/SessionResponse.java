package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SessionResponse {

    private Boolean authenticated;

    private SessionUser user;

    private Instant expiresAt;

    private Long idleTimeoutSeconds;
}
