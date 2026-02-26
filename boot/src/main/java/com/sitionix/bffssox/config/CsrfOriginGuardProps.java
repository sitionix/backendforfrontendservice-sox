package com.sitionix.bffssox.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Objects;

@ConfigurationProperties(prefix = "sitionix.csrf-origin-guard")
public record CsrfOriginGuardProps(
        boolean enabled,
        List<String> protectedPaths
) {

    public CsrfOriginGuardProps {
        protectedPaths = Objects.isNull(protectedPaths) ? List.of("/api/v1/auth/refresh") : List.copyOf(protectedPaths);
    }
}
